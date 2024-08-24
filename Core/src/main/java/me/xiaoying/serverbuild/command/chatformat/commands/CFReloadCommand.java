package me.xiaoying.serverbuild.command.chatformat.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class CFReloadCommand extends SCommand {
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

        ChatFormatModule chatFormatModule = (ChatFormatModule) SBPlugin.getModuleManager().getModule("ChatFormat");

        chatFormatModule.reload();

        sender.sendMessage(new VariableFactory(FileChatFormat.MESSAGE_RELOAD)
                        .prefix(FileChatFormat.SETTING_PREFIX)
                        .date(FileChatFormat.SETTING_DATEFORMAT)
                        .color()
                        .toString());
    }
}