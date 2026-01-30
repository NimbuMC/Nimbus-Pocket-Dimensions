package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.network.ClientPocketRooms;
import net.nimbu.thaumaturgy.persistentstates.PocketDimRoomsHelper;
import net.nimbu.thaumaturgy.renderer.PocketDimensionBorderRenderer;
import net.nimbu.thaumaturgy.spell.Spell;

import static net.nimbu.thaumaturgy.renderer.PocketDimensionBorderRenderer.BorderHeight;
import static net.nimbu.thaumaturgy.renderer.PocketDimensionBorderRenderer.BorderLength;

public class PocketDimensionExpansionSpell extends Spell {
    public PocketDimensionExpansionSpell() {
        super(Identifier.of(Thaumaturgy.MOD_ID,"textures/gui/spell_icons/expand_pocket_dimension_magic.png"),
                0x316B8FFF,
                0x3A7836FF);
    }

    private int raycastCooldown = 0;

    @Override
    public void renderReticle(World world, PlayerEntity user) {
        if (raycastCooldown-- <= 0) {
            BlockPos hit = raycastRoomFace(user);
            if(hit != null) {
                if (hit.getY() < 0 || hit.getY() > 29 || !PocketDimRoomsHelper.hasAdjacents(hit))
                    PocketDimensionBorderRenderer.expansionValid = false;
                else PocketDimensionBorderRenderer.expansionValid = true;
                PocketDimensionBorderRenderer.expansionModePosition = hit;
                raycastCooldown = 4;
            }
        }
        PocketDimensionBorderRenderer.expansionModeActive = true;
    }

    @Override
    public void cancelRenderReticle(World world, PlayerEntity user) {
        PocketDimensionBorderRenderer.expansionValid = false;
        PocketDimensionBorderRenderer.expansionModeActive = false;
    }

    @Override
    public void OnSpellEquip() {
        PocketDimensionBorderRenderer.expansionModeActive = true;
    }

    @Override
    public void OnSpellUnequip() {
        PocketDimensionBorderRenderer.expansionModeActive = false;
    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {
        BlockPos pos = raycastRoomFace(user);
        Thaumaturgy.LOGGER.info("Tried to use spell at position " + pos);
        if(pos != null) {
            if(PocketDimRoomsHelper.hasAdjacents(pos)) {
                if (pos.getY() > -1 && pos.getY() < 30) {
                    PocketDimRoomsHelper.addRoom((ServerWorld) world, pos);
                    BlockState state = Blocks.AIR.getDefaultState();
                    for (int x = 0; x < BorderLength; x++) {
                        for (int y = 0; y < BorderHeight; y++) {
                            for (int z = 0; z < BorderLength; z++) {
                                world.setBlockState(new BlockPos(
                                                x + BorderLength * pos.getX(),
                                                y + BorderHeight * pos.getY() + 7,
                                                z + BorderLength * pos.getZ())
                                        , state);
                            }
                        }
                    }
                }
            }
        }
    }

    private BlockPos raycastRoomFace(PlayerEntity user) {
        Vec3d camWorld = user.getCameraPosVec(1.0f);
        Vec3d dirWorld = user.getRotationVec(1.0f).normalize();

        Vec3d cam = new Vec3d(
                camWorld.x / BorderLength,
                (camWorld.y - 7) / BorderHeight,
                camWorld.z / BorderLength
        );

        Vec3d dir = new Vec3d(
                dirWorld.x / BorderLength,
                dirWorld.y / BorderHeight,
                dirWorld.z / BorderLength
        ).normalize();

        int stepX = dir.x > 0 ? 1 : -1;
        int stepY = dir.y > 0 ? 1 : -1;
        int stepZ = dir.z > 0 ? 1 : -1;

        double maxDistance = 4;
        double currentDistance = 0;
        Vec3d currentRayPos = cam.add(0,0,0);

        BlockPos outPos = null;

        do {
            double xRemaining = Math.abs(stepX * (1 - fract(cam.x)));
            double yRemaining = Math.abs(stepY * (1 - fract(cam.y)));
            double zRemaining = Math.abs(stepZ * (1 - fract(cam.z)));

            double tDeltaX = dir.x == 0 ? Double.POSITIVE_INFINITY : Math.abs(xRemaining / dir.x);
            double tDeltaY = dir.y == 0 ? Double.POSITIVE_INFINITY : Math.abs(yRemaining / dir.y);
            double tDeltaZ = dir.z == 0 ? Double.POSITIVE_INFINITY : Math.abs(zRemaining / dir.z);

            double dist = Math.min(Math.min(tDeltaX, tDeltaY), tDeltaZ);
            currentRayPos = currentRayPos.add(dir.multiply(dist));
            currentDistance += dist;
            outPos = new BlockPos(
                    (int)Math.round(currentRayPos.x - 0.5),
                    (int)Math.round(currentRayPos.y - 0.5),
                    (int)Math.round(currentRayPos.z - 0.5));

        } while (currentDistance < maxDistance && ClientPocketRooms.hasRoom(outPos));
        return !ClientPocketRooms.hasRoom(outPos) ? outPos : null;
    }

    private double fract(double k){return k - Math.floor(k);}

    @Override
    public String toString() {
        return "pocket_dimension_expansion_spell";
    }
}
