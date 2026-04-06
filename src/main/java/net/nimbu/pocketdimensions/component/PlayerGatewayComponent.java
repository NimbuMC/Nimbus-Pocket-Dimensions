package net.nimbu.pocketdimensions.component;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.Component;

public interface PlayerGatewayComponent extends Component {
    BlockPos getGatewayPos();
    void setGatewayPos(BlockPos pos);

    RegistryKey<World> getGatewayDim();
    void setGatewayDim(RegistryKey<World> dim);
}