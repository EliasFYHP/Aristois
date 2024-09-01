package me.xiaoying.serverbuild.gui;

import org.bukkit.inventory.InventoryHolder;

public interface GuiManager {
    /**
     * Register Gui
     *
     * @param gui Gui
     */
    void registerGui(Gui gui);

    /**
     * Get gui by name
     *
     * @param name GUI名称
     * @return GUI
     * @param <T> Gui
     */
    <T extends Gui> T getGui(String name);

    /**
     * Unregister gui
     *
     * @param name Gui 名称
     */
    void unregisterGui(String name);

    /**
     * Unregister all gui
     */
    void unregisterGuis();

    /**
     * Add gui to cache
     *
     * @param holder InventoryHolder
     * @param gui Gui
     */
    void addCacheGui(InventoryHolder holder, Gui gui);

    /**
     * Get gui in cache
     *
     * @param holder InventoryHolder
     */
    Gui getCacheGui(InventoryHolder holder);

    /**
     * Remove gui in cache
     *
     * @param holder InventoryHolder
     */
    void removeCacheGui(InventoryHolder holder);
}