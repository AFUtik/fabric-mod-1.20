package org.callofthevoid.screen.tabs;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class TabManager {
    protected List<Tab> tabs = new ArrayList<>();
    private int curIndex = 0;

    public void addTab(String displayName, Formatting formatting, int iconOffset, int index) {
        tabs.add(new Tab(List.of(Text.literal(displayName).setStyle(Style.EMPTY.withColor(formatting))), iconOffset, index));
    }

    public void switchTab(int index) {
        tabs.get(index).click();
        if (this.curIndex != index) {
            if (tabs.get(this.curIndex).isHasClicked()) {
                tabs.get(this.curIndex).click();
            }
            this.curIndex = index;
        }
    }

    public Tab getCurrentTab() {
        return tabs.get(curIndex);
    }

    public int getSize() {
        return tabs.size();
    }
}
