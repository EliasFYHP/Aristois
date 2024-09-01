package me.xiaoying.serverbuild.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Gui implements Cloneable {
    private String name;
    private List<Component> components = new ArrayList<>();
    private int height;

    public Gui(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Gui setName(String name) {
        this.name = name;
        return this;
    }

    public Component getComponent(int x, int y) {
        for (Component component : this.components) {
            if (component.getX() != x || component.getY() != y)
                continue;

            return component;
        }
        return null;
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public Gui addComponent(Component component) {
        this.components.add(component);
        return this;
    }

    public Gui removeComponent(Component component) {
        this.components.remove(component);
        return this;
    }

    public Gui removeComponent(int x, int y) {
        Iterator<Component> iterator = this.components.iterator();
        Component component;
        while (iterator.hasNext()) {
            component = iterator.next();

            if (component.getX() != x || component.getY() != y)
                continue;

            this.components.remove(component);
        }
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        if (height < 0 || height > 6)
            throw new RuntimeException(new IllegalArgumentException("GUI height need between 1 and 6."));

        this.height = height;
    }

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, this.height * 9, this.getName());
        this.components.forEach(component -> {
            if (component.getY() > 5 || component.getY() > 8)
                return;

            inventory.setItem(component.getY() * 9 + component.getX(), component.getItemStack());
        });
        return inventory;
    }

    public Gui clone() {
        try {
            Gui clone = (Gui) super.clone();
            clone.components = new ArrayList<>(this.components);
            return clone;
        } catch (CloneNotSupportedException var2) {
            throw new Error(var2);
        }
    }

    public <T extends Gui> T backup() {
        return (T) this.clone();
    }
}