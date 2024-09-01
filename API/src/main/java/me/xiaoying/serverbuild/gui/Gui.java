package me.xiaoying.serverbuild.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Gui implements Cloneable {
    private String name;
    private String displayName;
    private List<Component> components = new ArrayList<>();
    private int height;

    public Gui(String name) {
        this.name = name;
    }

    /**
     * Get gui name in gui manager
     *
     * @return gui's name in gui manager
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set alias of gui<br>
     * If you want to get this gui, you can use exact name of gui
     *
     * @param name alias name
     * @return Gui
     */
    public Gui setDisplayName(String name) {
        this.displayName = name;
        return this;
    }

    /**
     * Get alias of gui to use inventory title<br>
     *
     * @return String
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Get component by position
     *
     * @param x x-axis
     * @param y y-axis
     * @return Component
     */
    public Component getComponent(int x, int y) {
        for (Component component : this.components) {
            if (component.getX() != x || component.getY() != y)
                continue;

            return component;
        }
        return null;
    }

    /**
     * Get all component
     *
     * @return components
     */
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * Add new component to this gui
     *
     * @param component Component
     * @return Gui
     */
    public Gui addComponent(Component component) {
        this.components.add(component);
        return this;
    }

    /**
     * Remove component
     *
     * @param component Component
     * @return Gui
     */
    public Gui removeComponent(Component component) {
        this.components.remove(component);
        return this;
    }

    /**
     * Remove component by position
     *
     * @param x x-axis
     * @param y y-axis
     * @return
     */
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

    /**
     * Get height of gui<br>
     *
     * @return int
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set height of gui<br>
     * Height range is 1 to 6
     *
     * @param height
     */
    public void setHeight(int height) {
        if (height < 0 || height > 6)
            throw new RuntimeException(new IllegalArgumentException("GUI height need between 1 and 6."));

        this.height = height;
    }

    /**
     * Get inventory by gui
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        Inventory inventory;
        if (this.getDisplayName() == null)
            inventory = Bukkit.createInventory(null, this.height * 9, this.getName());
        else
            inventory = Bukkit.createInventory(null, this.height * 9, this.getDisplayName());
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

    protected <T extends Gui> T backup() {
        return (T) this.clone();
    }
}