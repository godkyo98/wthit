package mcp.mobius.waila.neo;

import mcp.mobius.waila.api.IModInfo;
import mcp.mobius.waila.service.ApiService;
import mcp.mobius.waila.util.ModInfo;
import net.minecraft.world.item.ItemStack;

public class NeoApiService extends ApiService {

    @Override
    @SuppressWarnings("DataFlowIssue")
    public IModInfo getModInfo(ItemStack stack) {
        return ModInfo.get(stack.getItem().getCreatorModId(stack));
    }

    @Override
    public String getDefaultEnergyUnit() {
        return "FE";
    }

}
