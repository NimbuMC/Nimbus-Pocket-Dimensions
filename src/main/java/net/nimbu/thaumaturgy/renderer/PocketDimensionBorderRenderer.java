package net.nimbu.thaumaturgy.renderer;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.entity.custom.PocketDimensionBorderControllerBlockEntity;
import net.nimbu.thaumaturgy.persistentstates.PocketDimRoomsHelper;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PocketDimensionBorderRenderer {
    public static void registerWorldRenderer() {
        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(
                    Identifier.of(Thaumaturgy.MOD_ID, "border"),
                    VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                    shader -> BORDER_SHADER = shader
            );
        });

        WorldRenderEvents.BEFORE_ENTITIES.register(PocketDimensionBorderRenderer::render);
    }

    private static void setLoadedChunk()
    {
        if(MinecraftClient.getInstance().world != null)
        {
            MinecraftClient.getInstance().world.getWorldChunk(new BlockPos(0,0,0)).setLoadedToWorld(true);
        }
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

    // Face normals: +X, -X, +Y, -Y, +Z, -Z
    private static final float[][] FACE_NORMALS = {{1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}};
    // [face][vertex][x, y, z, u, v]
    private static final float[][][] FACE_VERTICES = {
            { // +X (Z × Y = 16 × 12)
                    {16, 12, 16,  0, 12},
                    {16, 12, 0,  16, 12},
                    {16, 0, 0,   16, 0},
                    {16, 0, 16,  0, 0}
            },
            { // -X (Z × Y = 16 × 12)
                    {0, 12, 0,   0, 12},
                    {0, 12, 16,  16, 12},
                    {0, 0, 16,   16, 0},
                    {0, 0, 0,    0, 0}
            },
            { // +Y (X × Z = 16 × 16)
                    {0, 12, 16,   0, 16},
                    {16, 12, 16,  16, 16},
                    {16, 12, 0,   16, 0},
                    {0, 12, 0,    0, 0}
            },
            { // -Y (X × Z = 16 × 16)
                    {0, 0, 0,     0, 0},
                    {16, 0, 0,    16, 0},
                    {16, 0, 16,   16, 16},
                    {0, 0, 16,    0, 16}
            },
            { // +Z (X × Y = 16 × 12)
                    {0, 0, 16,    0, 0},
                    {16, 0, 16,   16, 0},
                    {16, 12, 16,  16, 12},
                    {0, 12, 16,   0, 12}
            },
            { // -Z (X × Y = 16 × 12)
                    {16, 0, 0,    0, 0},
                    {0, 0, 0,     16, 0},
                    {0, 12, 0,    16, 12},
                    {16, 12, 0,   0, 12}
            }
    };

    public static void render(WorldRenderContext context) {

        World currentWorld = context.world();
        MinecraftServer server = currentWorld.getServer();
        //if(currentWorld.getRegistryKey().getValue().toString().startsWith("thaumaturgy:pocket_dim")) return;
        MatrixStack matrices = new MatrixStack();
        Vector3f cam = MinecraftClient.getInstance().gameRenderer.getCamera().getPos().toVector3f();
        matrices.translate(-cam.x, -cam.y, -cam.z);
        matrices.push();
        VertexConsumerProvider.Immediate consumers = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        VertexConsumer vc = consumers.getBuffer(BORDER_RENDER_LAYER);
        AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (BORDER_SHADER != null && player != null) {
            BORDER_SHADER.getUniform("cameraPosition").set(
                    cam.x,
                    cam.y,
                    cam.z);
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
        matrices.pop();
        PocketDimensionBorderControllerBlockEntity entity = (PocketDimensionBorderControllerBlockEntity)currentWorld.getBlockEntity(BlockPos.ORIGIN);
        if(entity != null) {
            BlockPos relativePosition = new BlockPos((int) Math.floor(cam.x / 16), (int) Math.floor(cam.y / 12), (int) Math.floor(cam.z / 16));
            entity.roomStatus.put(relativePosition, true);
            final int renderRadius = 2;
            for (int x = -renderRadius; x <= renderRadius; x++) {
                for (int y = -renderRadius; y <= renderRadius; y++) {
                    for (int z = -renderRadius; z <= renderRadius; z++) {
                        boolean[] adj = PocketDimRoomsHelper.getAdjacents(new BlockPos(
                                relativePosition.getX() + x,
                                relativePosition.getY() + y,
                                relativePosition.getZ() + z));
                        for (int i = 0; i < 6; i++) {
                            if (adj[i]) renderFace(vc, matrices, i,
                                    relativePosition.getX() + x,
                                    relativePosition.getY() + y,
                                    relativePosition.getZ() + z);
                        }

                    }
                }
            }
        }
    }

    private static void renderFace(VertexConsumer vc, MatrixStack matrix, int index, int x, int y, int z)
    {
        Matrix4f mat = matrix.peek().getPositionMatrix();
        x *= 16; y *= 12; z *= 16;
        for (int i = 0; i < 4; i++) {
            vc.vertex(mat, FACE_VERTICES[index][i][0] + x, FACE_VERTICES[index][i][1] + y, FACE_VERTICES[index][i][2] + z)
                    .color(255, 255, 255, 255)
                    .normal(matrix.peek(), FACE_NORMALS[index][0], FACE_NORMALS[index][1], FACE_NORMALS[index][2])
                    .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                    .texture(FACE_VERTICES[index][i][3],FACE_VERTICES[index][i][4]);
        }
    }
}