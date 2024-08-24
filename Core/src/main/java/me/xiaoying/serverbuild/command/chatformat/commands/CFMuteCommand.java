package me.xiaoying.serverbuild.command.chatformat.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.ConstantCommon;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.utils.DateUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.sql.sentence.Condition;
import me.xiaoying.sql.sentence.Delete;
import me.xiaoying.sql.sentence.Insert;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Command(values = "mute", length = {1, 2})
public class CFMuteCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileChatFormat.MESSAGE_HELP)
                .prefix(FileChatFormat.SETTING_PREFIX)
                .date(FileChatFormat.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.cf.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MISSING_PERMISSION)
                            .prefix(FileChatFormat.SETTING_PREFIX)
                            .date(FileChatFormat.SETTING_DATEFORMAT)
                            .color()
                            .toString());
            return;
        }

        // create tables
        ChatFormatModule.createTables();

        Player player = Bukkit.getServer().getPlayerExact(strings[0]);
        String save = DateUtil.getDate(ConstantCommon.DATE_FORMAT);
        Date date;
        String over;
        if (strings.length == 1)
            date = DateUtil.translate(FileChatFormat.MUTE_DEFAULT_TIME, Date.class);
        else
            date = DateUtil.translate(strings[1], Date.class);

        if (date == null) {
            sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MUTE_WRONG)
                            .prefix(FileChatFormat.SETTING_PREFIX)
                            .date(FileChatFormat.SETTING_DATEFORMAT)
                            .color()
                            .toString());
            return;
        }

        over = DateUtil.getDate(DateUtil.getDate(date, ConstantCommon.DATE_FORMAT));

        if (player == null) {
            sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_NOT_FOUND_PLAYER)
                    .prefix(FileChatFormat.SETTING_PREFIX)
                    .date(FileChatFormat.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        Delete delete = new Delete(FileChatFormat.TABLE_MUTE);
        delete.condition(new Condition("uuid", player.getUniqueId().toString(), Condition.Type.EQUAL));

        Insert insert = new Insert(FileChatFormat.TABLE_MUTE);
        insert.insert(player.getUniqueId().toString(), save, over);
        SBPlugin.getSqlFactory().run(delete, insert);


        // calculate time
        long lastTime = DateUtil.getDateReduce(over, save, ConstantCommon.DATE_FORMAT) / 1000;
        player.sendMessage(new VariableFactory(FileChatFormat.MUTE_MESSAGE)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .time(lastTime)
                        .color()
                        .toString());

        sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_MUTE_SUCCESS)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .time(lastTime)
                        .color()
                        .toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1)
            ServerUtil.getOnlinePlayers().forEach(player -> list.add(player.getName()));
        else if (strings.length == 2)
            list.add("10s");

        List<String> conditionList = new ArrayList<>();
        for (String s : list) {
            if (!s.toUpperCase(Locale.ENGLISH).startsWith(strings[strings.length - 1].toUpperCase(Locale.ENGLISH)))
                continue;

            conditionList.add(s);
        }
        return conditionList;
    }
}