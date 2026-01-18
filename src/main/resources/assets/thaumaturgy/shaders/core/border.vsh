#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec3 vPos;
out vec2 vUv;
out vec2 vLightUv;
out vec3 vNormal;
out vec4 vColor;

void main() {
    vPos = Position;
    vUv = UV0;
    vLightUv = UV0;
    vNormal = Normal;
    vColor = Color;

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
}
