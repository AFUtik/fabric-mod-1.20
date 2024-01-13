package org.callofthevoid.screen.tabs;

import java.util.ArrayList;
import java.util.List;

public class TabManager {
    protected List<Tab> tabs = new ArrayList<>();
    private int curIndex = 0;

    public void addTab(Tab tab) {
        tabs.add(tab);
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
