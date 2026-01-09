package net.nimbu.thaumaturgy.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Wellspring extends Block {
    //these below allow for properties of instances of blocks
    public static final BooleanProperty CLICKED = BooleanProperty.of("clicked");


    public Wellspring(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(CLICKED,false)); //makes clicked start as false
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient()){ //if server
            world.setBlockState(pos, state.cycle(CLICKED)); //false to true or true to false
        }

        return ActionResult.SUCCESS; //gives swing animation
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLICKED);
    }
}
