#version 150

in vec3 vWorldPos;
in vec4 vColor;
in vec2 vUv;
in vec2 vLightUv;
in vec3 vNormal;

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform float GameTime;

const int MAX_PLAYERS = 8;
uniform float playerPositions[24];
uniform int playerCount;
uniform float time; // pass a steadily increasing time

out vec4 fragColor;

// 4x4 Bayer matrix for dithering
float bayer4x4(vec2 p) {
    int x = int(mod(p.x, 4.0));
    int y = int(mod(p.y, 4.0));
    int index = x + y * 4;
    float[16] dither = float[16](
    0.0/16.0,  8.0/16.0,  2.0/16.0, 10.0/16.0,
    12.0/16.0,  4.0/16.0, 14.0/16.0,  6.0/16.0,
    3.0/16.0, 11.0/16.0,  1.0/16.0,  9.0/16.0,
    15.0/16.0,  7.0/16.0, 13.0/16.0,  5.0/16.0
    );
    return dither[index];
}

float hash(vec3 p) {
    p = fract(p * 0.3183099 + vec3(0.1,0.2,0.3)); // scale & offset
    p *= 17.0;
    return fract(p.x * p.y * p.z * (p.x + p.y + p.z));
}

float noise(vec3 p) {
    // Grid cell
    vec3 i = floor(p);
    vec3 f = fract(p);
    // Smooth interpolation
    vec3 u = f*f*(3.0-2.0*f);
    // Hash corners
    float n000 = hash(i + vec3(0.0, 0.0, 0.0));
    float n100 = hash(i + vec3(1.0, 0.0, 0.0));
    float n010 = hash(i + vec3(0.0, 1.0, 0.0));
    float n110 = hash(i + vec3(1.0, 1.0, 0.0));
    float n001 = hash(i + vec3(0.0, 0.0, 1.0));
    float n101 = hash(i + vec3(1.0, 0.0, 1.0));
    float n011 = hash(i + vec3(0.0, 1.0, 1.0));
    float n111 = hash(i + vec3(1.0, 1.0, 1.0));
    // Trilinear interpolation
    float nx00 = mix(n000, n100, u.x);
    float nx10 = mix(n010, n110, u.x);
    float nx01 = mix(n001, n101, u.x);
    float nx11 = mix(n011, n111, u.x);
    float nxy0 = mix(nx00, nx10, u.y);
    float nxy1 = mix(nx01, nx11, u.y);
    return mix(nxy0, nxy1, u.z);
}

void main() {
    float scale = 16.0;
    float slowTime = time * 0.2f;

    vec3 pixelWorld = (floor(vWorldPos * scale + vNormal * 0.5) / scale);

    float minDist = 1e6f;
    for (int i = 0; i < playerCount * 3; i += 3) {
        float d = length(pixelWorld - vec3(playerPositions[i], playerPositions[i + 1], playerPositions[i + 2]));
        if (d < minDist) minDist = d;
    }
    float fade = smoothstep(3.0, 4.0, minDist);

    // Bayer dither
    vec2 texel = floor(vUv * scale);
    float threshold = bayer4x4(texel);


    // Base texture
    vec2 glitchUV = texel/scale;

    float glitchStrength = 0.0625;

    // Compute pseudo-random 2D glitch vector
    vec2 glitch = vec2(
    noise(pixelWorld + slowTime * 0.075),
    noise(pixelWorld - slowTime * 0.0125)
    );

    // Compute glitch magnitude
    float glitchMag = length(glitch);

    // Only apply glitch if the magnitude is high enough
    if(glitchMag > 0.99) glitchUV += glitchStrength * (glitch / glitchMag);


    vec4 tex = texture(Sampler0, glitchUV);

    if (fade > threshold) discard;
    if (fade > threshold * 0.5) tex.a *= 0.5;

    vec4 light = texture(Sampler2, glitchUV);
    // Glow band near fade edge
    float glow = 1.0 - abs(fade - 0.5) * 2.0;
    glow = pow(clamp(glow, 0.0, 1.0), 2.0);
    float alpha = clamp(tex.a + glow,0,1);
    if(alpha < 0.01) discard;

    // Twinkle: occasionally push color toward white based on noise + time
    float twinkle = noise(pixelWorld * 45.67 + slowTime * 0.1);
    twinkle = smoothstep(0.5, 1.0, twinkle) * 0.4f; // only strong twinkles
    vec3 emissive = vec3(0.3, 0.5, 1.0) * glow * 0.8 + vec3(twinkle);

    // Combine texture, vertex color, light, emissive
    vec3 color = tex.rgb * vColor.rgb * light.rgb + emissive;
    fragColor = vec4(color, alpha);
}