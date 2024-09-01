package me.xiaoying.serverbuild.gui;

import org.bukkit.inventory.ItemStack;

public class Component {
    private final ItemStack itemStack;
    private int x;
    private int y;
    private Runnable click;
    private Runnable hover;
    private boolean close = true;

    public Component(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Component setClick(Runnable runnable) {
        this.click = runnable;
        return this;
    }

    public void onClick() {
        if (this.click == null)
            return;

        this.click.run();
    }

    public Component setHover(Runnable runnable) {
        this.hover = runnable;
        return this;
    }

    public void onHover(Runnable runnable) {
        this.hover = runnable;
    }

    public Component setX(int x) {
        this.x = x;
        return this;
    }

    public int getX() {
        return this.x;
    }

    public Component setY(int y) {
        this.y = y;
        return this;
    }

    public int getY() {
        return this.y;
    }

    public boolean needClose() {
        return this.close;
    }

    public Component setClose(boolean needClose) {
        this.close = needClose;
        return this;
    }
}