package org.callofthevoid.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;

public class SimplePropertyDelegate implements PropertyDelegate {
    private int progress = 0;
    private int maxProgress;
    private final int size;

    public SimplePropertyDelegate(int maxProgress, int size) {
        this.maxProgress = maxProgress;
        this.size = size;
    }

    public void increaseCraftProgress() {
        this.progress++;
    }

    public boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    public void resetProgress() {
        this.progress = 0;
    }

    public void writeNbt(String id, NbtCompound nbt) {
        nbt.putInt(id + ".progress", progress);
    }

    public void readNbt(String id, NbtCompound nbt) {
        this.progress = nbt.getInt(id + ".progress");
    }

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> this.progress;
            case 1 -> this.maxProgress;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> this.progress = value;
            case 1 -> this.maxProgress = value;
        }
    }

    @Override
    public int size() {
        return size;
    }

}
