package me.xiaoying.serverbuild.scheduler;

import me.xiaoying.serverbuild.constant.MessageAnnouncerConstant;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.entity.Player;

/**
 * Scheduler MessageAnnouncer
 */
public class MessageAnnouncerScheduler extends Scheduler {
    private int time = 0;

    public MessageAnnouncerScheduler() {
        super(Type.SYNC_DELAY, 20L);
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            if (this.time < MessageAnnouncerConstant.MESSAGE_ANNOUNCER_DELAY) {
                this.time++;
                return;
            }



            for (Player onlinePlayer : ServerUtil.getOnlinePlayers()) {
            }


            MessageAnnouncerScheduler.this.time++;
        };
    }
}