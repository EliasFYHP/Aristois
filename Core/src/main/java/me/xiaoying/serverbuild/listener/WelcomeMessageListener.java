package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.WelcomeMessageEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileWelcomeMessage;
import me.xiaoying.serverbuild.module.WelcomeMessageModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WelcomeMessageListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        WelcomeMessageEntity entity = null;
        WelcomeMessageModule module = (WelcomeMessageModule) SBPlugin.getModuleManager().getModule("WelcomeMessage");

        for (WelcomeMessageEntity welcomeMessageEntity : module.getWelcomeMessageEntities()) {
            if (!ServerUtil.hasPermission(player, "sb.wm.admin", "sb.admin", welcomeMessageEntity.getPermission()) && !player.isOp())
                continue;

            if (entity == null) {
                entity = welcomeMessageEntity;
                continue;
            }

            if (entity.getPriority() < welcomeMessageEntity.getPriority())
                continue;

            entity = welcomeMessageEntity;
        }

        if (entity == null)
            return;

        event.setJoinMessage(null);
        for (String s : entity.getJoin().split("\n")) {
            s = new VariableFactory(s)
                    .prefix(FileWelcomeMessage.SETTING_PREFIX)
                    .date(FileWelcomeMessage.SETTING_DATEFORMAT)
                    .placeholder(player)
                    .color()
                    .toString();
            SBPlugin.getScriptManager().performScript(s, player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        WelcomeMessageEntity entity = null;
        WelcomeMessageModule module = (WelcomeMessageModule) SBPlugin.getModuleManager().getModule("WelcomeMessage");

        for (WelcomeMessageEntity welcomeMessageEntity : module.getWelcomeMessageEntities()) {
            if (!ServerUtil.hasPermission(player, "sb.wm.admin", "sb.admin", welcomeMessageEntity.getPermission()) && !player.isOp())
                continue;

            if (entity == null) {
                entity = welcomeMessageEntity;
                continue;
            }

            if (entity.getPriority() < welcomeMessageEntity.getPriority())
                continue;

            entity = welcomeMessageEntity;
        }

        if (entity == null)
            return;

        event.setQuitMessage(null);
        for (String s : entity.getQuit().split("\n")) {
            s = new VariableFactory(s)
                    .prefix(FileWelcomeMessage.SETTING_PREFIX)
                    .date(FileWelcomeMessage.SETTING_DATEFORMAT)
                    .placeholder(player)
                    .color()
                    .toString();
            SBPlugin.getScriptManager().performScript(s, player);
        }
    }
}