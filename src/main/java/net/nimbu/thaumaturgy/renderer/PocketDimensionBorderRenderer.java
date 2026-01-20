package net.nimbu.thaumaturgy.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionBorderBlockEntity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PocketDimensionBorderRenderer implements BlockEntityRenderer<PocketDimensionBorderBlockEntity> {
    public PocketDimensionBorderRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    public static final Identifier BORDER_TEXTURE = Identifier.of(Thaumaturgy.MOD_ID, "textures/block/near_border.png");
    private static ShaderProgram BORDER_SHADER;
    private static final RenderLayer BORDER_RENDER_LAYER = RenderLayer.of(
            "thaumaturgy_border",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(new RenderPhase.ShaderProgram(PocketDimensionBorderRenderer::getShader))
                    .texture(new RenderPhase.Texture(BORDER_TEXTURE,false,false))
                    .transparency(RenderPhase.Transparency.TRANSLUCENT_TRANSPARENCY)
                    .depthTest(RenderPhase.DepthTest.LEQUAL_DEPTH_TEST)
                    .writeMaskState(RenderPhase.ALL_MASK)
                    .cull(RenderPhase.Cull.DISABLE_CULLING)
                    .lightmap(RenderPhase.DISABLE_LIGHTMAP)
                    .build(false)
    );

    private static ShaderProgram getShader() {
        return BORDER_SHADER;
    }

    public static void register() {
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(
                    Identifier.of(Thaumaturgy.MOD_ID, "border"),
                    VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                    shader -> BORDER_SHADER = shader
            );
        });
    }
    // Face normals: +X, -X, +Y, -Y, +Z, -Z
    private static final float[][] FACE_NORMALS = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};

    @Override
    public void render(PocketDimensionBorderBlockEntity entity, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider consumers,
                       int light, int overlay) {

        matrices.push();
        VertexConsumer vc = consumers.getBuffer(BORDER_RENDER_LAYER);
        Matrix4f mat = matrices.peek().getPositionMatrix();
        AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (BORDER_SHADER != null && player != null) {
            Vector3f cam = MinecraftClient.getInstance().gameRenderer.getCamera().getPos().toVector3f();
            BORDER_SHADER.getUniform("cameraPosition").set(
                    cam.x,
                    cam.y,
                    cam.z);
            var world = MinecraftClient.getInstance().world;

            if (world != null) {
                var players = world.getPlayers();
                int count = Math.min(players.size(), 8);

                BORDER_SHADER.getUniform("playerCount").set(count);

                float[] data = new float[8 * 3]; // 8 vec3
                if (count > 0) {
                    Vector3f playerPos = player.getPos().toVector3f();
                    data[0] = playerPos.x;
                    data[1] = playerPos.y;
                    data[2] = playerPos.z;
                }

                if (count > 1) {
                    for (int i = 1; i < count; i++) {
                        Vec3d p = players.get(i).getPos();
                        int base = i * 3;
                        data[base] = (float) p.x;
                        data[base + 1] = (float) p.y;
                        data[base + 2] = (float) p.z;
                    }
                }

                for (int i = count; i < 8; i++) {
                    int base = i * 3;
                    data[base] = data[base + 1] = data[base + 2] = 0.0f;
                }

                BORDER_SHADER.getUniform("playerPositions").set(data);
            }
            float t = MinecraftClient.getInstance().world.getTime() + tickDelta;
            BORDER_SHADER.getUniform("time").set(t);
        }


        /*
        // +X
        renderFace(vc, mat, matrices.peek(), 0,
                1,1,1,  0,0,
                1,1,0,  1,0,
                1,0,0,  1,1,
                1,0,1,  0,1
        );
        // -X
        renderFace(vc, mat, matrices.peek(), 1,
                0,1,0,  0,0,
                0,1,1,  1,0,
                0,0,1,  1,1,
                0,0,0,  0,1
        );
         */
        // +Y
        renderFace(vc, mat, matrices.peek(), 2,
                0,1,1,  0,0,
                1,1,1,  1,0,
                1,1,0,  1,1,
                0,1,0,  0,1
        );
        /*
        // -Y
        renderFace(vc, mat, matrices.peek(), 3,
                0,0,0,  0,0,
                1,0,0,  1,0,
                1,0,1,  1,1,
                0,0,1,  0,1
        );
        // +Z
        renderFace(vc, mat, matrices.peek(), 4,
                0,0,1,  0,0,
                1,0,1,  1,0,
                1,1,1,  1,1,
                0,1,1,  0,1
        );
        // -Z
        renderFace(vc, mat, matrices.peek(), 5,
                1,0,0,  0,0,
                0,0,0,  1,0,
                0,1,0,  1,1,
                1,1,0,  0,1
        );

         */

        matrices.pop();
    }


    private void renderFace(VertexConsumer vc, Matrix4f mat, MatrixStack.Entry norMat, int index,
                            double x1, double y1, double z1, double u1, double v1,
                            double x2, double y2, double z2, double u2, double v2,
                            double x3, double y3, double z3, double u3, double v3,
                            double x4, double y4, double z4, double u4, double v4
    )
    {
        vc.vertex(mat, (float) x1, (float) y1, (float) z1)
                .color(255, 255, 255, 255)
                .normal(norMat, FACE_NORMALS[index][0], FACE_NORMALS[index][1], FACE_NORMALS[index][2])
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .texture((float) u1, (float) v1);
        vc.vertex(mat, (float) x2, (float) y2, (float) z2)
                .color(255, 255, 255, 255)
                .normal(norMat, FACE_NORMALS[index][0], FACE_NORMALS[index][1], FACE_NORMALS[index][2])
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .texture((float) u2, (float) v2);
        vc.vertex(mat, (float) x3, (float) y3, (float) z3)
                .color(255, 255, 255, 255)
                .normal(norMat, FACE_NORMALS[index][0], FACE_NORMALS[index][1], FACE_NORMALS[index][2])
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .texture((float) u3, (float) v3);
        vc.vertex(mat, (float) x4, (float) y4, (float) z4)
                .color(255, 255, 255, 255)
                .normal(norMat, FACE_NORMALS[index][0], FACE_NORMALS[index][1], FACE_NORMALS[index][2])
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .texture((float) u4, (float) v4);
    }
}