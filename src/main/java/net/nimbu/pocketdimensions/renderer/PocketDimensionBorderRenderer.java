package net.nimbu.pocketdimensions.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.network.ClientPocketDimensionPersistentState;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PocketDimensionBorderRenderer {

    public static final int BorderLength = 13;
    public static final int BorderHeight = 10;

    public static void registerWorldRenderer() {
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(
                    Identifier.of(PocketDimensions.MOD_ID, "border"),
                    VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                    shader -> BORDER_SHADER = shader
            );
        });
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(
                    Identifier.of(PocketDimensions.MOD_ID, "farViewBorder"),
                    VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                    shader -> FAR_BORDER_SHADER = shader
            );
        });

        WorldRenderEvents.END.register(ctx -> {
            if (MinecraftClient.getInstance().world == null) {
                expansionModeActive = false;
                expansionModePosition = null;
            }
        });
        WorldRenderEvents.AFTER_ENTITIES.register(PocketDimensionBorderRenderer::render);
    }

    public static boolean expansionModeActive = false;
    public static BlockPos expansionModePosition = null;
    public static boolean expansionValid = true;

    public static final Identifier BORDER_TEXTURE = Identifier.of(PocketDimensions.MOD_ID, "textures/block/near_border.png");
    private static ShaderProgram BORDER_SHADER;
    private static ShaderProgram FAR_BORDER_SHADER;
    private static final RenderLayer BORDER_RENDER_LAYER = RenderLayer.of(
            "pocketdimensions_border",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(new RenderPhase.ShaderProgram(PocketDimensionBorderRenderer::getShader))
                    .texture(new RenderPhase.Texture(BORDER_TEXTURE, false, false))
                    .transparency(RenderPhase.Transparency.NO_TRANSPARENCY)
                    .depthTest(RenderPhase.DepthTest.LEQUAL_DEPTH_TEST)
                    .writeMaskState(RenderPhase.ALL_MASK)
                    .cull(RenderPhase.Cull.DISABLE_CULLING)
                    .lightmap(RenderPhase.DISABLE_LIGHTMAP)
                    .build(false)
    );

    private static final RenderLayer CONSTANT_BORDER_RENDER_LAYER = RenderLayer.of(
            "pocketdimensions_border",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(new RenderPhase.ShaderProgram(PocketDimensionBorderRenderer::getFarShader))
                    .texture(new RenderPhase.Texture(BORDER_TEXTURE, false, false))
                    .transparency(RenderPhase.Transparency.NO_TRANSPARENCY)
                    .depthTest(RenderPhase.DepthTest.LEQUAL_DEPTH_TEST)
                    .writeMaskState(RenderPhase.ALL_MASK)
                    .cull(RenderPhase.Cull.DISABLE_CULLING)
                    .lightmap(RenderPhase.DISABLE_LIGHTMAP)
                    .build(false)
    );

    private static final RenderLayer EXPANSION_RETICLE_LAYER = RenderLayer.of(
            "pocketdimensions_border",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(new RenderPhase.ShaderProgram(PocketDimensionBorderRenderer::getFarShader))
                    .texture(new RenderPhase.Texture(BORDER_TEXTURE,false, false))
                    .transparency(RenderPhase.Transparency.NO_TRANSPARENCY)
                    .depthTest(RenderPhase.DepthTest.LEQUAL_DEPTH_TEST)
                    .writeMaskState(RenderPhase.ALL_MASK)
                    .cull(RenderPhase.Cull.DISABLE_CULLING)
                    .lightmap(RenderPhase.DISABLE_LIGHTMAP)
                    .build(false)
    );


    private static ShaderProgram getShader() {
        return BORDER_SHADER;
    }
    private static ShaderProgram getFarShader(){return FAR_BORDER_SHADER;}

    // Face normals: +X, -X, +Y, -Y, +Z, -Z
    private static final float[][] FACE_NORMALS = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};
    // [face][vertex][x, y, z, u, v]
    private static final float[][][] FACE_VERTICES = {
            { // +X (Z × Y = BorderLength × BorderHeight)
                    {BorderLength, BorderHeight, BorderLength, 0, BorderHeight},
                    {BorderLength, BorderHeight, 0, BorderLength, BorderHeight},
                    {BorderLength, 0, 0, BorderLength, 0},
                    {BorderLength, 0, BorderLength, 0, 0}
            },
            { // -X (Z × Y = BorderLength × BorderHeight)
                    {0, BorderHeight, 0, 0, BorderHeight},
                    {0, BorderHeight, BorderLength, BorderLength, BorderHeight},
                    {0, 0, BorderLength, BorderLength, 0},
                    {0, 0, 0, 0, 0}
            },
            { // +Y (X × Z = BorderLength × BorderLength)
                    {0, BorderHeight, BorderLength, 0, BorderLength},
                    {BorderLength, BorderHeight, BorderLength, BorderLength, BorderLength},
                    {BorderLength, BorderHeight, 0, BorderLength, 0},
                    {0, BorderHeight, 0, 0, 0}
            },
            { // -Y (X × Z = BorderLength × BorderLength)
                    {0, 0, 0, 0, 0},
                    {BorderLength, 0, 0, BorderLength, 0},
                    {BorderLength, 0, BorderLength, BorderLength, BorderLength},
                    {0, 0, BorderLength, 0, BorderLength}
            },
            { // +Z (X × Y = BorderLength × BorderHeight)
                    {0, 0, BorderLength, 0, 0},
                    {BorderLength, 0, BorderLength, BorderLength, 0},
                    {BorderLength, BorderHeight, BorderLength, BorderLength, BorderHeight},
                    {0, BorderHeight, BorderLength, 0, BorderHeight}
            },
            { // -Z (X × Y = BorderLength × BorderHeight)
                    {BorderLength, 0, 0, 0, 0},
                    {0, 0, 0, BorderLength, 0},
                    {0, BorderHeight, 0, BorderLength, BorderHeight},
                    {BorderLength, BorderHeight, 0, 0, BorderHeight}
            }
    };

    public static void render(WorldRenderContext context) {
        if (!ClientPocketDimensionPersistentState.isClientInPocketDimension()) return;
        MatrixStack matrices = context.matrixStack();
        Vector3f cam = MinecraftClient.getInstance().gameRenderer.getCamera().getPos().toVector3f();
        matrices.translate(-cam.x, -cam.y, -cam.z);
        matrices.push();
        VertexConsumerProvider.Immediate consumers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        VertexConsumer vc = consumers.getBuffer(
                expansionModeActive ? CONSTANT_BORDER_RENDER_LAYER : BORDER_RENDER_LAYER
        );
        AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (BORDER_SHADER != null && player != null) {
            BORDER_SHADER.getUniform("cameraPosition").set(cam.x, cam.y, cam.z);
            var world = MinecraftClient.getInstance().world;

            if (world != null) {
                var players = world.getPlayers();
                int count = Math.min(players.size(), 8);

                BORDER_SHADER.getUniform("playerCount").set(count);

                float[] data = new float[8 * 3];
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
            float t = MinecraftClient.getInstance().world.getTime();
            BORDER_SHADER.getUniform("time").set(t);
        }
        BlockPos relativePosition = new BlockPos((int) Math.floor(cam.x / BorderLength), (int) Math.floor(cam.y / BorderHeight), (int) Math.floor(cam.z / BorderLength));
        final int renderRadius = 2;
        for (int x = -renderRadius; x <= renderRadius; x++) {
            for (int y = -renderRadius; y <= renderRadius; y++) {
                for (int z = -renderRadius; z <= renderRadius; z++) {

                    BlockPos currentPos = new BlockPos(
                            relativePosition.getX() + x,
                            relativePosition.getY() + y,
                            relativePosition.getZ() + z
                    );

                    //skip faced wall if looking at expansion (green) wall
                    if (expansionModeActive && expansionModePosition != null
                            && currentPos.equals(expansionModePosition)) {
                        continue;
                    }

                    boolean[] adj = ClientPocketDimensionPersistentState.getAdjacents(currentPos);

                    if (expansionModeActive && expansionModePosition != null) {

                        for (int i = 0; i < 6; i++) {

                            int nx = (int) FACE_NORMALS[i][0];
                            int ny = (int) FACE_NORMALS[i][1];
                            int nz = (int) FACE_NORMALS[i][2];

                            BlockPos neighbour = currentPos.add(nx, ny, nz);

                            if (neighbour.equals(expansionModePosition)) {
                                adj[i] = false;
                            }
                        }
                    }

                    for (int i = 0; i < 6; i++) {
                        if (adj[i]) {
                            renderFace(vc, matrices, i,
                                    currentPos.getX(),
                                    currentPos.getY(),
                                    currentPos.getZ());
                        }
                    }
                }
            }
        }
        matrices.pop();

        if (expansionModeActive && expansionModePosition != null) {
            matrices.push();
            FAR_BORDER_SHADER.getUniform("cameraPosition").set(cam.x, cam.y, cam.z);
            var world = MinecraftClient.getInstance().world;
            if (world != null) {
                var players = world.getPlayers();
                int count = Math.min(players.size(), 8);

                FAR_BORDER_SHADER.getUniform("playerCount").set(count);

                float[] data = new float[8 * 3];
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

                FAR_BORDER_SHADER.getUniform("playerPositions").set(data);
            }
            float t = MinecraftClient.getInstance().world.getTime();
            FAR_BORDER_SHADER.getUniform("time").set(t);

            // Keep world-space positioning
            matrices.translate(
                    expansionModePosition.getX() * BorderLength,
                    expansionModePosition.getY() * BorderHeight + 7,
                    expansionModePosition.getZ() * BorderLength
            );

            VertexConsumer expVc = consumers.getBuffer(EXPANSION_RETICLE_LAYER);
            Matrix4f mat = matrices.peek().getPositionMatrix();
            if(expansionValid) {
                for (int i = 0; i < 6; i++) {
                    for (int v = 0; v < 4; v++) {
                        expVc.vertex(mat, FACE_VERTICES[i][v][0], FACE_VERTICES[i][v][1], FACE_VERTICES[i][v][2])
                                .color(0, 255, 0, 255)
                                .texture(FACE_VERTICES[i][v][3], FACE_VERTICES[i][v][4])
                                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                                .normal(FACE_NORMALS[i][0], FACE_NORMALS[i][1], FACE_NORMALS[i][2]);
                    }
                }
            }
            matrices.pop();
//            matrices.translate(
//                    -(expansionModePosition.getX() * BorderLength),
//                    -(expansionModePosition.getY() * BorderHeight - 7),
//                    -(expansionModePosition.getZ() * BorderLength)
//            );
        }
        matrices.translate(cam.x, cam.y, cam.z);
        consumers.draw();
    }

    private static void renderFace(VertexConsumer vc, MatrixStack matrix, int index, int x, int y, int z) {
        Matrix4f mat = matrix.peek().getPositionMatrix();
        x *= BorderLength;
        y *= BorderHeight;
        z *= BorderLength;
        y += 7;
        for (int i = 0; i < 4; i++) {
            vc.vertex(mat, FACE_VERTICES[index][i][0] + x, FACE_VERTICES[index][i][1] + y, FACE_VERTICES[index][i][2] + z)
                    .color(255, 255, 255, 255)
                    .normal(matrix.peek(), FACE_NORMALS[index][0], FACE_NORMALS[index][1], FACE_NORMALS[index][2])
                    .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                    .texture(FACE_VERTICES[index][i][3], FACE_VERTICES[index][i][4]);
        }
    }
}