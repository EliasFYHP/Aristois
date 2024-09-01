package me.xiaoying.serverbuild.gui;

import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {
    private final Map<String, Gui> guis = new HashMap<>();
    private final Map<InventoryHolder, Gui> cacheGui = new HashMap<>();

    public GuiManager() {
        SBPlugin.getInstance().getServer().getPluginManager().registerEvents(new GuiListener(), SBPlugin.getInstance());
    }

    public void registerGui(Gui gui) {
        this.guis.put(gui.getName(), gui);
    }

    public <T extends Gui> T getGui(String name) {
        return this.guis.get(name).backup();
    }

    /**
     * 取消注册 Gui
     *
     * @param name Gui 名称
     */
    public void unregisterGui(String name) {
        this.guis.remove(name);
    }

    /**
     * 取消注册 Gui
     */
    public void unregisterGuis() {
        this.guis.clear();
    }

    /**
     * 添加缓存 Gui
     *
     * @param holder InventoryHolder
     * @param gui Gui
     */
    public void addCacheGui(InventoryHolder holder, Gui gui) {
        this.cacheGui.put(holder, gui);
    }

    /**
     * 获取缓存 Gui
     *
     * @param holder InventoryHolder
     */
    public Gui getCacheGui(InventoryHolder holder) {
        return this.cacheGui.get(holder);
    }

    /**
     * 移除缓存 Gui
     *
     * @param holder InventoryHolder
     */
    public void removeCacheGui(InventoryHolder holder) {
        this.cacheGui.remove(holder);
    }
}