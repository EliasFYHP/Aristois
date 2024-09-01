package me.xiaoying.serverbuild.gui;

import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimpleGuiManager implements GuiManager {
    private Listener listener = new GuiListener();
    private final Map<String, Gui> guis = new HashMap<>();
    private final Map<InventoryHolder, Gui> cacheGui = new HashMap<>();

    public SimpleGuiManager() {
        ServerUtil.registerEvent(this.listener);
    }

    public void unInitialize() {
        ServerUtil.unregisterListener(this.listener);
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

        Iterator<Gui> iterator = this.cacheGui.values().iterator();
        Gui gui;
        while (iterator.hasNext() && (gui = iterator.next()) != null) {
            if (!gui.getName().equalsIgnoreCase(name))
                continue;

            iterator.remove();
        }
    }

    /**
     * 取消注册 Gui
     */
    public void unregisterGuis() {
        this.guis.clear();
        this.cacheGui.values().forEach(holder -> holder.getInventory().getViewers().forEach(HumanEntity::closeInventory));
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
        holder.getInventory().getViewers().forEach(HumanEntity::closeInventory);
    }
}