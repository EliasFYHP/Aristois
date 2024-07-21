package me.xiaoying.serverbuild.command.chatformat.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.utils.DateUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.sentence.Condition;
import me.xiaoying.sql.sentence.Delete;
import me.xiaoying.sql.sentence.Insert;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Command(values = "mute", length = {1, 2})
public class ChatFormatMuteCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(ChatFormatConstant.MESSAGE_HELP)
                .prefix(ChatFormatConstant.SETTING_PREFIX)
                .date(ChatFormatConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.cf.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_MISSING_PERMISSION)
                            .prefix(ChatFormatConstant.SETTING_PREFIX)
                            .date(ChatFormatConstant.SETTING_DATEFORMAT)
                            .color()
                            .toString());
            return;
        }

        // create tables
        ChatFormatModule.createTables();

        Player player = Bukkit.getServer().getPlayerExact(strings[0]);
        String save = DateUtil.getDate(ChatFormatConstant.SETTING_DATEFORMAT);
        String over = null;
        if (strings.length == 1)
            over = DateUtil.getDate(DateUtil.translate(ChatFormatConstant.MUTE_DEFAULT_TIME, Date.class), ChatFormatConstant.SETTING_DATEFORMAT);
        else
            over = DateUtil.getDate(DateUtil.translate(strings[1], Date.class), ChatFormatConstant.SETTING_DATEFORMAT);

        if (player == null) {
            sender.sendMessage(new VariableFactory(ChatFormatConstant.MESSAGE_NOT_FOUND_PLAYER)
                    .prefix(ChatFormatConstant.SETTING_PREFIX)
                    .date(ChatFormatConstant.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        Delete delete = new Delete(ChatFormatConstant.TABLE_MUTE);
        delete.condition(new Condition("uuid", player.getUniqueId().toString(), Condition.Type.EQUAL));

        Insert insert = new Insert(ChatFormatConstant.TABLE_MUTE);
        insert.insert(player.getUniqueId().toString(), save, over);
        SBPlugin.getSqlFactory().run(delete, insert);


        // calculate time
        long lastTime = DateUtil.getDateReduce(over, save, ChatFormatConstant.SETTING_DATEFORMAT);
        player.sendMessage(new VariableFactory(ChatFormatConstant.MUTE_MESSAGE)
                        .prefix(ChatFormatConstant.SETTING_PREFIX)
                        .date(ChatFormatConstant.SETTING_DATEFORMAT)
                        .time(lastTime)
                        .color()
                        .toString());

        sender.sendMessage(new VariableFactory(ChatFormatConstant.MUTE_MESSAGE)
                        .prefix(ChatFormatConstant.SETTING_PREFIX)
                        .date(ChatFormatConstant.SETTING_DATEFORMAT)
                        .time(lastTime)
                        .color()
                        .toString());
    }
}