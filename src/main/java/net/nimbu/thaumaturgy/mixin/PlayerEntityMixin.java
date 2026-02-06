package net.nimbu.thaumaturgy.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.item.SpellEquipHandler;
import net.nimbu.thaumaturgy.particle.ModParticleTypes;
import org.lwjgl.system.windows.WNDCLASSEX;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements SpellEquipHandler{

    private int spellEquipFlags;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataMixin(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("Thaumaturgy:SpellUnlocks", spellEquipFlags);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataMixin(NbtCompound nbt, CallbackInfo ci) {
        spellEquipFlags = nbt.getInt("Thaumaturgy:SpellUnlocks");
    }

    public boolean getSpellUnlockFlags(int index) {
        return (spellEquipFlags & (1 << index)) != 0; //return flag truth at masked position
    }

    public void setSpellUnlockFlags(int index, boolean value) {
        if (value) {
            spellEquipFlags |= (1 << index);
        } else {
            spellEquipFlags &= ~(1 << index);
        }
    }



    //--------------------Soaring Spell-----------------

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "travel(Lnet/minecraft/util/math/Vec3d;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void soaring(Vec3d movementInput, CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity)(Object)this;

        if (this.shouldFly()) {

            World world = this.getWorld();
            if (!world.isClient() && random.nextInt(5) == 0){
                Position pos = this.getPos();
                ((ServerWorld) world).spawnParticles(ModParticleTypes.RISE_PARTICLE,
                        pos.getX(), pos.getY()+1, pos.getZ(), 1, 0.25, 0.5, 0.25, 0);
            }

            self.getAbilities().setFlySpeed(0.01f);
            Vec3d velocity = this.getVelocity();
            super.travel(new Vec3d(movementInput.x, 0, movementInput.z));
            double newY = velocity.y;

            if (movementInput.y == 0) {
                newY *= 0.9;
            }
            this.setVelocity(this.getVelocity().x, newY, this.getVelocity().z);

            this.onLanding();
            this.setFlag(Entity.FALL_FLYING_FLAG_INDEX, false);
            ci.cancel(); //skip vanilla
        }
        else{
            self.getAbilities().setFlySpeed(0.05f); //reset to default
        }
    }

    @ModifyReturnValue(
            method = "getOffGroundSpeed()F",
            at = @At("RETURN")
    )
    private float modifyFlySpeed(float original) {
        if (this.shouldFly()) {
            return original * 2.0F; // ← reduce vertical speed
        }
        return original;
    }

    private boolean shouldFly() {
        //flag control
        PlayerEntity self = (PlayerEntity)(Object)this;
        if (self.getAbilities().flying && !this.hasVehicle()){
            return true;
        }
        return false;
    }
}




/*@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements SpellEquipHandler {



    //-----------------Soaring Spell------------------

//    THIS ONLY EFFECTS CAMERA AND HITBOX
//    @Inject(method = "updatePose", at = @At("TAIL"))
//    private void forceStandingPose(CallbackInfo ci) {
//        PlayerEntity player = (PlayerEntity)(Object)this;
//        player.setPose(EntityPose.STANDING);
//    }

    @Inject(
            method = "travel",
            at = @At("HEAD"),
            cancellable = true
    )
    private void handleSoaring(Vec3d movementInput, CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity)(Object)this;

        //player.setPose(EntityPose.STANDING);

        if (player.isDead()) return;
        if (player.isSpectator()) return;

        //TODO:
        //read flight state
        //replace these with however storing player data
        boolean wantsToFly = false;   // set from intent packet
        boolean isSoaring   = false;   // current flight state
        int soaringMetre  = 0;    // remaining energy

        boolean canFly =
                wantsToFly
                        && soaringMetre > 0
                        && !player.isFallFlying()
                        && !player.hasVehicle();

        if (canFly) {
            isSoaring = true;
        } else {
            isSoaring = false;
        }

        isSoaring=true;

        if (isSoaring) {
            player.setNoGravity(true); //prevent gravity
            double xVelocity = 0;
            double yVelocity = 0;
            double zVelocity = 0;

            Vec3d velocity = new Vec3d(0, 0, 0);

            player.updateVelocity(1,velocity);



            soaringMetre --;
        } else {
            if (player.isOnGround()) soaringMetre ++;
        }

        //TODO:
        // Persist + sync state if needed:
        // save flightMeter
        // save isFlying
        // sync to client if changed
    }

}*/