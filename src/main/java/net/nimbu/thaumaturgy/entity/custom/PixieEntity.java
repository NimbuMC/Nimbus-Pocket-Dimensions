package net.nimbu.thaumaturgy.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

public class PixieEntity extends BeeEntity {

    public final AnimationState flyingAnimationState = new AnimationState();

    public PixieEntity(EntityType<? extends BeeEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3F);
    }

    private void setupAnimationStates(){
        this.flyingAnimationState.startIfNotRunning(this.age);
    }

    @Override
    public void tick() {
        super.tick();

        this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));


        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }
}















//Below is the proper code I cant get to work (dont know what causes entities to fly).
//Must find the code that applies movement to entities? maybe the flying code is there?







/*package net.nimbu.thaumaturgy.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class PixieEntity extends AnimalEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationCooldown = 0;

    public PixieEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }




    @Override
    protected void initGoals() {
        //ctrl h on "goal" to view all
        //other mobs like "FoxEntity" can be viewed to see their goals
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.AMETHYST_SHARD), false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        //---------------------------
        //this.goalSelector.add(3, new FleeEntityGoal<>(this, 1.0D)); -> I want to make the pixies try to flee if approached by a player that isnt sneaking / they flee from noise?
        //---------------------------
        //this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 4.0F));
        //this.goalSelector.add(5, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10);
                //.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D)
                //.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20)
                //.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6F);
    }

    private void setupAnimationStates(){
        if (this.idleAnimationCooldown<=0){
            this.idleAnimationCooldown=10; //idle animation length hardcoded
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationCooldown;
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));


        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    @Override
    protected boolean isFlappingWings() {
        return true;
    }

    @Override
    protected void addFlapEffects() {
        super.addFlapEffects();
        //this.setVelocity();
    }

    //@Override
    //protected void addAirTravelEffects() {
    //   super.addAirTravelEffects();
    //}

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.AMETHYST_SHARD);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PIXIE.create(world);
    }
}*/
