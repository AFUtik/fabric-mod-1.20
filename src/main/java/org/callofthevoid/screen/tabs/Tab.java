package org.callofthevoid.screen.tabs;

import net.minecraft.text.Text;

import java.util.List;

public class Tab {

    private final List<Text> displayName;
    private final int iconOffset;
    private boolean hasClicked = false;
    private int index;

    public Tab(List<Text> displayName, int iconOffset, int index) {
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
        if (this.hasClicked) {
            this.hasClicked = false;
        } else {
            this.hasClicked = true;
        }
    }
}
