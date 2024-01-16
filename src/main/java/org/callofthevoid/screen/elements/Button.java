package org.callofthevoid.screen.elements;

import net.minecraft.text.Text;

import java.util.List;

public class Button {

    private final List<Text> displayName;
    private final int iconOffset;
    private boolean hasClicked = false;
    private int index;

    public Button(List<Text> displayName, int iconOffset, int index) {
        this.displayName = displayName;
        this.iconOffset = iconOffset;
        this.index = index;
    }

    public List<Text> getDisplayName() {
        return this.displayName;
    }

    public int getIconOffset() {
        return iconOffset;
    }

    public int getIndex() {
        return index;
    }

    public boolean isHasClicked() {
        return hasClicked;
    }

    public void click() {
        this.hasClicked = !this.hasClicked;
    }
}
