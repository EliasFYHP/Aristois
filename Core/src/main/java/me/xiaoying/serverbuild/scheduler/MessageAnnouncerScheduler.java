package me.xiaoying.serverbuild.scheduler;

import me.xiaoying.serverbuild.constant.MessageAnnouncerConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.MessageAnnouncerEntity;
import me.xiaoying.serverbuild.module.MessageAnnouncerModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Scheduler MessageAnnouncer
 */
public class MessageAnnouncerScheduler extends Scheduler {
    private int time = 0;
    private int number = 0;

    public MessageAnnouncerScheduler() {
        super(Type.SYNC_REPEAT, 20L);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            if (this.time < MessageAnnouncerConstant.MESSAGE_ANNOUNCER_DELAY) {
                this.time++;
                return;
            }

            MessageAnnouncerModule module = (MessageAnnouncerModule) SBPlugin.getModuleManager().getModule("MessageAnnouncer");
            if (module.getAnnouncers().isEmpty())
                return;

            if (this.number >= module.getAnnouncers().size())
                this.number = 0;

            MessageAnnouncerEntity entity = module.getAnnouncers().get(this.number);
            entity.getScripts().forEach(string -> SBPlugin.getScriptManager().performScript(string, Bukkit.getServer().getConsoleSender()));
            this.time = 0;
            this.number++;
        };
    }
}