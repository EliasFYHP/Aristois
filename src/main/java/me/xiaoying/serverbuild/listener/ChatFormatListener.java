package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.ChatFormatEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.utils.PlayerUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener ChatFormat
 */
public class ChatFormatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setCancelled(true);

        // color
        if (event.getMessage().contains("&") && (!player.isOp() && !PlayerUtil.hasPermission(player, "sb.admin", "sb.cf.admin", "sb.cf.color"))) {
            player.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_MISSING_PERMISSION)
                    .date(ChatFormatConstant.SETTING_DATEFORMAT)
                    .prefix(ChatFormatConstant.SETTING_PREFIX)
                    .player(player)
                    .placeholder(player)
                    .color()
                    .toString());
            return;
        }

        // character limit
        if (ChatFormatConstant.CHARACTER_LIMIT_ENABLE & message.length() > ChatFormatConstant.CHARACTER_LIMIT_LIMIT) {
            player.sendMessage(new VariableFactory(ChatFormatConstant.CHARACTER_LIMIT_MESSAGE)
                            .date(ChatFormatConstant.SETTING_DATEFORMAT)
                            .prefix(ChatFormatConstant.SETTING_PREFIX)
                            .player(player)
                            .placeholder(player)
                            .color()
                            .toString());
            return;
        }

        boolean send = true;
        // black terms
        if (ChatFormatConstant.BLACK_TERMS_ENABLE) {
            for (String string : ChatFormatConstant.BLACK_TERMS_TERMS) {
                if (!StringUtil.match(string, message, 0, 0) && message.contains(string))
                    continue;

                if (!ChatFormatConstant.BLACK_TERMS_CANCEL)
                    send = false;

                if (ChatFormatConstant.BLACK_TERMS_FOR_EVERY_BODY) {
                    player.sendMessage(new VariableFactory(ChatFormatConstant.BLACK_TERMS_MESSAGE)
                            .player(player)
                            .date(ChatFormatConstant.SETTING_DATEFORMAT)
                            .color()
                            .toString());
                    String[] split = ChatFormatConstant.BLACK_TERMS_SCRIPT.split("\n");
                    for (String s : split)
                        SBPlugin.getScriptManager().performScript(s, player);
                    break;
                }

                if (player.isOp() || PlayerUtil.hasPermission(player, "sb.admin", "sb.cf.admin", "sb.cf.jump"))
                    return;

                player.sendMessage(new VariableFactory(ChatFormatConstant.BLACK_TERMS_MESSAGE)
                        .player(player)
                        .date(ChatFormatConstant.SETTING_DATEFORMAT)
                        .color()
                        .toString());
                String[] split = ChatFormatConstant.BLACK_TERMS_SCRIPT.split("\n");
                for (String s : split)
                    SBPlugin.getScriptManager().performScript(s, player);
            }
        }

        // get ChatFormatEntity
        ChatFormatModule module = (ChatFormatModule) SBPlugin.getModuleManager().getModule("ChatFormat");
        ChatFormatEntity entity = null;
        for (ChatFormatEntity chatFormatEntity : module.getChatFormatEntities()) {
            if (!player.hasPermission(chatFormatEntity.getPermission()))
                continue;

            if (entity == null) {
                entity = chatFormatEntity;
                continue;
            }

            if (entity.getPriority() < chatFormatEntity.getPriority())
                continue;

            entity = chatFormatEntity;
        }

        // handle entity
        if (entity != null) {
            String format = entity.getFormat();
            format = new VariableFactory(format).message(message).toString();
            message = format;
        }
        message = new VariableFactory(message)
                .date(ChatFormatConstant.SETTING_DATEFORMAT)
                .prefix(ChatFormatConstant.SETTING_PREFIX)
                .player(player)
                .placeholder(player)
                .color()
                .toString();

        // match @
        if (ChatFormatConstant.CALL_ENABLE) {
            List<Player> atPlayers = this.at(message);
            for (Player atPlayer : atPlayers) {
                message = message.replace(ChatFormatConstant.CALL_KEY + atPlayer.getName(), "&b" + ChatFormatConstant.CALL_KEY + atPlayer.getName());
                atPlayer.playSound(player.getLocation(), ChatFormatConstant.CALL_SOUND, 1F, 0F);
            }
        }

        if (!send)
            return;

        for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
            onlinePlayer.sendMessage(message);

        ServerUtil.sendMessage(message);
    }

    private List<Player> at(String message) {
        if (message.contains("@"))
            return new ArrayList<>();

        Player player = null;
        boolean match = false;
        List<Player> players = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : message.split("")) {
            if (s.equalsIgnoreCase(ChatFormatConstant.CALL_KEY)) {
                if (stringBuilder.length() != 0) {
                    Player playerExact = Bukkit.getServer().getPlayerExact(stringBuilder.toString());
                    if (playerExact != null) players.add(playerExact);
                    stringBuilder = new StringBuilder();
                }
                match = true;
            }

            // 是否处于匹配模式
            if (!match)
                continue;

            stringBuilder.append(s);
            Player p;
            if ((p = Bukkit.getServer().getPlayerExact(stringBuilder.toString())) != null)
                player = p;
        }
        if (player != null)
            players.add(player);

        return players;
    }
}