package org.callofthevoid.screen.elements;

import java.util.ArrayList;
import java.util.List;

public class ButtonManager {
    private List<Button> buttons = new ArrayList<>();
    private int curIndex = 0;

    public void addButton(Button button) {
        buttons.add(button);
    }

    public List<Button> getButtons() {
        return this.buttons;
    }

    public void switchTab(int index) {
        buttons.get(index).click();
        if (this.curIndex != index) {
            if (buttons.get(this.curIndex).isHasClicked()) {
                buttons.get(this.curIndex).click();
            }
            this.curIndex = index;
        }
    }

    public Button getCurrentButton() {
        return buttons.get(curIndex);
    }

    public int getSize() {
        return buttons.size();
    }
}
