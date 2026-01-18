#version 150

in vec3 vPos;
in vec4 vColor;
in vec2 vUv;
in vec2 vLightUv;

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform float playerPositionX;
uniform float playerPositionY;
uniform float playerPositionZ;

out vec4 fragColor;

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

void main() {
    float scale = 16.0;
    float dist = length((floor(vPos * scale) / scale + vec3(0.5 / scale)) - vec3(playerPositionX, playerPositionY, playerPositionZ));

    // Fade distance in blocks
    float fade = smoothstep(3.0, 4.0, dist);

    // Stable texel coordinates
    vec2 texel = floor(vUv * scale);

    // Glow band near fade edge
    float glow = 1.0 - abs(fade - 0.5) * 2.0;
    glow = pow(clamp(glow, 0.0, 1.0), 2.0);

    // Dither threshold
    float threshold = bayer4x4(texel);
    vec4 tex = texture(Sampler0, vUv);
    if (fade > threshold) discard;
    if (fade > threshold / 2.0) tex.a *= 0.5;

    vec4 light = texture(Sampler2, vLightUv);

    // Emissive color (blue-ish, tweak freely)
    vec3 emissive = vec3(0.3, 0.5, 1.0) * glow * 0.8;

    vec3 color = tex.rgb * vColor.rgb * light.rgb + emissive;

    fragColor = vec4(color, 1.0);
}


