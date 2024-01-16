package org.callofthevoid.screen.renderer;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.screen.elements.Button;
import org.callofthevoid.screen.elements.ButtonManager;
import org.callofthevoid.util.MouseUtil;

import java.util.List;
import java.util.Optional;

public class ButtonRenderer extends ButtonManager {
    private static final Identifier ICONS = new Identifier(CallOfTheVoid.MOD_ID, "textures/gui/icons.png");
    private static final Identifier DEFAULT_TAB = new Identifier(CallOfTheVoid.MOD_ID, "textures/gui/tabs/tab.png");
    private int space; //Space between tabs
    private int width; // Weight of a button in pixels
    private int height; // Height of a button in pixels

    private int x;
    private int y;
    public ButtonRenderer(int x, int y, int offsetX, int offsetY, int width, int height, int space) {
        this.x = x - (width + offsetX);
        this.y = y + offsetY;
        this.width = width;
        this.height = height;
        this.space = space;
    }

    public void addDefaultButtons() {
        Button button = new Button(List.of(Text.literal("Redstone Mode").setStyle(Style.EMPTY.withColor(Formatting.RED))), 0,0);
        this.getButtons().add(button);
    }

    public void draw(DrawContext context) {
        int offsetHeight = 0;
        for (Button button : this.getButtons()) {
            if (button.isHasClicked()) {
                context.drawTexture(ICONS, this.x, this.y + offsetHeight, button.getIconOffset(), 0, width, height);
            } else {
                context.drawTexture(ICONS, this.x, this.y + offsetHeight, button.getIconOffset(), height, width, height);
            }
            offsetHeight += height + space;
        }
    }

    public void renderTooltip(DrawContext context, TextRenderer textRenderer, int pMouseX, int pMouseY) {
        Button shownButton = this.getButton(pMouseX, pMouseY);
        if (shownButton != null && isMouseAboveArea(pMouseX, pMouseY, x, y, 0, shownButton.getIndex() * (height + space), width, height)) {
            context.drawTooltip(textRenderer, this.getTooltip(shownButton),
                    Optional.empty(), pMouseX - (this.x + width), pMouseY - this.y);
        }
    }

    public void mouseClick(double pMouseX, double pMouseY) {
        Button clickedButton = this.getButton((int) pMouseX, (int) pMouseY);
        if (clickedButton != null) {
            this.switchTab(clickedButton.getIndex());
        }
    }

    private Button getButton(int pMouseX, int pMouseY) {
        int offsetY = 0;
        for (Button button : this.getButtons()) {
            if(isMouseAboveArea(pMouseX, pMouseY, this.x, this.y, 0, offsetY, width, height)) {
                return button;
            } else {
                offsetY += width + space;
            }
        }
        return null;
    }

    private List<Text> getTooltip(Button button) {
        return button.getDisplayName();
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.width;
    }
}
