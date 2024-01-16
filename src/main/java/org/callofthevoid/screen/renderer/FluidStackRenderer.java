package org.callofthevoid.screen.renderer;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.callofthevoid.CallOfTheVoid;
import org.callofthevoid.util.FluidStack;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// CREDIT: https://github.com/mezz/JustEnoughItems by mezz (Forge Version)
// HIGHLY EDITED VERSION FOR FABRIC by Kaupenjoe
// Under MIT-License: https://github.com/mezz/JustEnoughItems/blob/1.18/LICENSE.txt
public class FluidStackRenderer implements IIngredientRenderer<FluidStack> {
    private static final Identifier FLUID_TEX = new Identifier(CallOfTheVoid.MOD_ID, "textures/fluids/fluids.png");
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    public final long capacityMb;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;
    private Formatting formatting;

    enum TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }

    public FluidStackRenderer(long capacityMb, boolean showCapacity, int width, int height, int offsetX, int offsetY, Formatting formatting) {
        this(capacityMb, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height, offsetX, offsetY, formatting);
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public FluidStackRenderer(int capacityMb, boolean showCapacity, int width, int height, int offsetX, int offsetY, Formatting formatting) {
        this(capacityMb, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height, offsetX, offsetY, formatting);
    }

    private FluidStackRenderer(long capacityMb, TooltipMode tooltipMode, int width, int height, int offsetX, int offsetY, Formatting formatting) {
        Preconditions.checkArgument(capacityMb > 0, "capacity must be > 0");
        Preconditions.checkArgument(width > 0, "width must be > 0");
        Preconditions.checkArgument(height > 0, "height must be > 0");
        this.capacityMb = capacityMb;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.formatting = formatting;
    }

    /*
    * METHOD FROM https://github.com/TechReborn/TechReborn
    * UNDER MIT LICENSE: https://github.com/TechReborn/TechReborn/blob/1.19/LICENSE.md
    */
    public void drawFluid(DrawContext context, FluidStack fluid, int u, int v, int x, int y, long maxCapacity) {
        if (fluid.getFluidVariant().getFluid() == Fluids.EMPTY) {
            return;
        }
        y += this.height;

        final int drawHeight = (int) (fluid.getAmount() / (maxCapacity * 1F) * this.height);
        int offsetHeight = drawHeight;

        int iteration = 0;
        while (offsetHeight != 0) {
            final int curHeight = offsetHeight < this.height ? offsetHeight : this.height;

            context.drawTexture(FLUID_TEX, x + offsetX, (y + offsetY) - offsetHeight, u, v, width, curHeight);
            offsetHeight -= curHeight;
            iteration++;
            if (iteration > 50) {
                break;
            }
        }
    }

    @Override
    public List<Text> getTooltip(FluidStack fluidStack, TooltipContext tooltipFlag) {
        List<Text> tooltip = new ArrayList<>();
        FluidVariant fluidType = fluidStack.getFluidVariant();
        if (fluidType == null) {
            return tooltip;
        }


        String fluid = Registries.FLUID.getId(fluidStack.fluidVariant.getFluid()).toTranslationKey();
        MutableText displayName = Text.translatable("block." + fluid);
        if (Objects.equals(fluid, "minecraft.empty")) {
            displayName.setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY));
        } else {
            displayName.setStyle(Style.EMPTY.withColor(formatting));
        }
        tooltip.add(displayName);

        long amount = fluidStack.getAmount();
        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
            MutableText amountString = Text.translatable("callofthevoid.tooltip.liquid.amount.with.capacity", nf.format(amount), nf.format(capacityMb));
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
        } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
            MutableText amountString = Text.translatable("callofthevoid.tooltip.liquid.amount", nf.format(amount));
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)));
        }

        return tooltip;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    public int getOffsetX() {
        return offsetX;
    }


    public int getOffsetY() {
        return offsetY;
    }
}