package me.xiaoying.serverbuild.command.serverbuild.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Command(values = "script", length = -1)
public class SBScriptCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(ConfigConstant.OVERALL_SITUATION_MESSAGE_HELP)
                .prefix(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX)
                .date(ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.script") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(ConfigConstant.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION)
                            .prefix(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX)
                            .date(ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                            .color()
                            .toString());
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(new VariableFactory(ConfigConstant.OVERALL_SITUATION_MESSAGE_NEED_PLAYER)
                            .prefix(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX)
                            .date(ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                            .color()
                            .toString());
            return;
        }
        Player player = (Player) sender;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }
        SBPlugin.getScriptManager().performScript(stringBuilder.toString(), player);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1)
            list = SBPlugin.getScriptManager().getScripts();

        List<String> conditionList = new ArrayList<>();
        for (String s : list) {
            if (!s.toUpperCase(Locale.ENGLISH).startsWith(strings[strings.length - 1].toUpperCase(Locale.ENGLISH)))
                continue;

            conditionList.add(s);
        }
        return conditionList;
    }
}