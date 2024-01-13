package org.callofthevoid.screen.renderer;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.screen.tabs.Tab;
import org.callofthevoid.screen.tabs.TabManager;
import org.callofthevoid.util.MouseUtil;

import java.util.List;
import java.util.Optional;

public class TabRenderer extends TabManager {
    private static final Identifier ICONS = new Identifier(CallOfTheVoid.MOD_ID, "textures/gui/icons.png");
    private final int space; //Space between tabs
    private final int weight; // Weight of a tab in pixels
    private final int height; // Height of a tab in pixels

    private int x;
    private int y;
    public TabRenderer(int weight, int height, int x, int y, int offsetX, int offsetY, int space) {
        this.x = x - (weight + offsetX);
        this.y = y + offsetY;
        this.weight = weight;
        this.height = height;
        this.space = space;
    }
    public void draw(DrawContext context) {
        int offsetHeight = 0;
        for (Tab tab : tabs) {
            if (tab.isHasClicked()) {
                context.drawTexture(ICONS, this.x, this.y + offsetHeight, tab.getIconOffset(), 0, weight, height);
            } else {
                context.drawTexture(ICONS, this.x, this.y + offsetHeight, tab.getIconOffset(), 20, weight, height);
            }
            offsetHeight += this.height + this.space;
        }
    }

    public void renderTooltip(DrawContext context, TextRenderer textRenderer, int pMouseX, int pMouseY) {
        Tab shownTab = this.getTab(pMouseX, pMouseY);
        if (shownTab != null && isMouseAboveArea(pMouseX, pMouseY, x, y, 0, shownTab.getIndex() * (height + space), weight, height)) {
            context.drawTooltip(textRenderer, this.getTooltip(shownTab),
                    Optional.empty(), pMouseX - (this.x + weight), pMouseY - this.y);
        }
    }

    public void mouseClick(double pMouseX, double pMouseY) {
        Tab clickedTab = this.getTab((int) pMouseX, (int) pMouseY);
        if (clickedTab != null) {
            this.switchTab(clickedTab.getIndex());
        }
    }

    private Tab getTab(int pMouseX, int pMouseY) {
        int offsetY = 0;
        for (Tab tab : tabs) {
            if(isMouseAboveArea(pMouseX, pMouseY, this.x, this.y, 0, offsetY, this.weight, this.height)) {
                return tab;
            } else {
                offsetY += weight + space;
            }
        }
        return null;
    }

    private List<Text> getTooltip(Tab tab) {
        return tab.getDisplayName();
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }
}
