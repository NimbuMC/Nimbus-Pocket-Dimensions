#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 cameraPosition;

out vec3 vWorldPos;
out vec2 vUv;
out vec2 vLightUv;
out vec4 vColor;
out vec3 vNormal;

void main() {
    // Position is camera-relative → restore world space
    vWorldPos = Position + cameraPosition;

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vUv = UV0;
    vLightUv = UV0;
    vColor = Color;
    vNormal = Normal;
}
