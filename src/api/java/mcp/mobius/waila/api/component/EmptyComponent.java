package mcp.mobius.waila.api.component;

import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.api.ITooltipComponent;

/**
 * A tooltip component that renders nothing.
 */
public enum EmptyComponent implements ITooltipComponent {

    INSTANCE;

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float delta) {
    }

}
