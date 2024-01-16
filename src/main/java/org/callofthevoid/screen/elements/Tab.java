package org.callofthevoid.screen.elements;

import net.minecraft.util.Identifier;

public class Tab extends ButtonManager {
    private int width;
    private int height;
    private Identifier texture;

    public Tab(Identifier texture, int width, int height) {
        this.texture = texture;

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Identifier getTexture() {
        return texture;
    }
}
