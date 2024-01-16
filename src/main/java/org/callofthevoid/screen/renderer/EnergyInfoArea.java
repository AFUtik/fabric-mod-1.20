package org.callofthevoid.screen.renderer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import team.reborn.energy.api.EnergyStorage;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense" (FORGE VERSION)
 *  Details can be found in the license file in the root folder of this project
 */
public class EnergyInfoArea extends InfoArea {
    private static final Identifier TEXTURE = new Identifier(CallOfTheVoid.MOD_ID, "textures/gui/gui-extractor.png");
    private final EnergyStorage energy;
    private final int offsetX;
    private final int offsetY;

    public EnergyInfoArea(int xMin, int yMin, int offsetX, int offsetY, EnergyStorage energy, int width, int height)  {
        super(new Rect2i(xMin, yMin, width, height));
        this.energy = energy;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public List<Text> getTooltips() {
        return List.of(Text.literal(energy.getAmount()+"/"+energy.getCapacity()+" E").setStyle(Style.EMPTY.withColor(Formatting.RED)));
    }

    @Override
    public void draw(DrawContext context) {
        final int height = area.getHeight();

        int y = area.getY() + height;

        final int stored = (int)(height*(energy.getAmount()/(float)energy.getCapacity()));
        int offsetHeight = stored;

        int iteration = 0;
        while (offsetHeight != 0) {
            final int curHeight = offsetHeight < height ? offsetHeight : height;
            context.drawTexture(TEXTURE, area.getX() + offsetX, (y + offsetY) - offsetHeight, 176, 68, area.getWidth(), curHeight);
            offsetHeight -= curHeight;
            iteration++;
            if (iteration > 50) {
                break;
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {}


    public int getWidth() {
        return area.getWidth();
    }


    public int getHeight() {
        return area.getHeight();
    }


    public int getOffsetX() {
        return offsetX;
    }


    public int getOffsetY() {
        return offsetY;
    }
}