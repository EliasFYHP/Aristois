package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.ChatFormatEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.utils.DateUtil;
import me.xiaoying.serverbuild.utils.PlayerUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import me.xiaoying.sql.entity.Table;
import me.xiaoying.sql.sentence.Condition;
import me.xiaoying.sql.sentence.Delete;
import me.xiaoying.sql.sentence.Select;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listener ChatFormat
 */
public class ChatFormatListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setCancelled(true);

        // mute
        ChatFormatModule.createTables();
        List<String> columns = new ArrayList<>();
        columns.add("uuid");
        columns.add("save");
        columns.add("over");
        Select select = new Select(columns, ChatFormatConstant.TABLE_MUTE);
        select.condition(new Condition("uuid", player.getUniqueId().toString(), Condition.Type.EQUAL));
        List<Table> run = SBPlugin.getSqlFactory().run(select);
        if (run.size() != 0 && run.get(0).getRecords().size() != 0) {
            String over = (String) run.get(0).getRecords().get(0).get("over");
            long lastTime;
            if ((lastTime = DateUtil.getDateReduce(over, DateUtil.getDate(ConstantCommon.DATE_FORMAT), ConstantCommon.DATE_FORMAT)) > 0) {
                player.sendMessage(new VariableFactory(ChatFormatConstant.MUTE_MESSAGE)
                        .prefix(ChatFormatConstant.SETTING_PREFIX)
                        .date(ChatFormatConstant.SETTING_DATEFORMAT)
                        .time(lastTime / 1000)
                        .color()
                        .toString());
                return;
            }

            Delete delete = new Delete(ChatFormatConstant.TABLE_MUTE);
            delete.condition(new Condition("uuid", player.getUniqueId().toString(), Condition.Type.EQUAL));
            SBPlugin.getSqlFactory().run(delete);
        }

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
                if (!StringUtil.match(string, message, 0, 0) && !message.contains(string))
                    continue;

                if (ChatFormatConstant.BLACK_TERMS_CANCEL)
                    send = false;

                if (ChatFormatConstant.BLACK_TERMS_FOR_EVERY_BODY) {
                    player.sendMessage(new VariableFactory(ChatFormatConstant.BLACK_TERMS_MESSAGE)
                                    .prefix(ChatFormatConstant.SETTING_PREFIX)
                                    .player(player)
                                    .date(ChatFormatConstant.SETTING_DATEFORMAT)
                                    .placeholder(player)
                                    .color()
                                    .toString());
                    String[] split = ChatFormatConstant.BLACK_TERMS_SCRIPT.split("\n");
                    for (String s : split)
                        SBPlugin.getScriptManager().performScript(s, player);
                    break;
                }

                if (player.isOp() || PlayerUtil.hasPermission(player, "sb.admin", "sb.cf.admin", "sb.cf.jump")) {
                    send = true;
                    break;
                }

                player.sendMessage(new VariableFactory(ChatFormatConstant.BLACK_TERMS_MESSAGE)
                        .prefix(ChatFormatConstant.SETTING_PREFIX)
                        .player(player)
                        .date(ChatFormatConstant.SETTING_DATEFORMAT)
                        .placeholder(player)
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
                message = new VariableFactory(message.replace(ChatFormatConstant.CALL_KEY + atPlayer.getName(), "&b" + ChatFormatConstant.CALL_KEY + atPlayer.getName() + "&f")).color().toString();
                atPlayer.playSound(atPlayer.getLocation(), ChatFormatConstant.CALL_SOUND, 1F, 0F);
            }
        }

        if (!send)
            return;

        TextComponent textComponent = null;
        item: if (message.contains("[item]")) {
            textComponent = new TextComponent();
            if (player.getItemInUse() == null || player.getItemInUse().getType() == Material.AIR)
                break item;

            String[] split = message.split("\\[itme]");
            for (int i = 0; i < split.length; i++) {
                textComponent.addExtra(split[i]);

                if (i == split.length - 1)
                    break;

                TextComponent textComponent1 = new TextComponent(player.getItemInUse().getItemMeta().getDisplayName());
                textComponent1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM));
                textComponent.addExtra(textComponent1);
            }

            if (message.endsWith("[item]")) {
                TextComponent textComponent1 = new TextComponent(player.getItemInUse().getItemMeta().getDisplayName());
                textComponent1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM));
                textComponent.addExtra(textComponent1);
            }
        }

        if (textComponent == null) {
            for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
                onlinePlayer.sendMessage(message);

            ServerUtil.sendMessage(message);
            return;
        }

        for (Player onlinePlayer : ServerUtil.getOnlinePlayers())
            onlinePlayer.spigot().sendMessage(textComponent);

        ServerUtil.sendMessage(textComponent.getText());
    }

    private List<Player> at(String message) {
        if (!message.contains(ChatFormatConstant.CALL_KEY))
            return new ArrayList<>();

        Player player = null;
        boolean match = false;
        List<Player> players = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : message.split("")) {
            if (s.equalsIgnoreCase(ChatFormatConstant.CALL_KEY)) {
                if (stringBuilder.toString().length() != 0) {
                    players.add(player);
                    stringBuilder = new StringBuilder();
                }
                match = true;
                continue;
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