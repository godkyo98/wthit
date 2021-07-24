package mcp.mobius.waila.plugin.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;

public enum ComposterComponent implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendBody(List<Component> tooltip, IBlockAccessor accessor, IPluginConfig config) {
        if (config.get(WailaVanilla.CONFIG_COMPOSTER_LEVEL)) {
            BlockState state = accessor.getBlockState();
            tooltip.add(new TranslatableComponent("tooltip.waila.compost_level", state.getValue(ComposterBlock.LEVEL)));
        }
    }
}
