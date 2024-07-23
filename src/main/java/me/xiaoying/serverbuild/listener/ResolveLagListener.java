package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 * Listener ResolveLag
 */
public class ResolveLagListener implements Listener {
    @EventHandler
    public void onChunkLoaded(ChunkLoadEvent event) {
        ResolveLagModule module = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");
        module.addChunk(event.getChunk());
    }
}