package me.xiaoying.serverbuild.command.welcomemessage.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileWelcomeMessage;
import me.xiaoying.serverbuild.module.WelcomeMessageModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class WMReloadCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileWelcomeMessage.MESSAGE_HELP)
                .prefix(FileWelcomeMessage.SETTING_PREFIX)
                .date(FileWelcomeMessage.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.wm.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileWelcomeMessage.MESSAGE_MISSING_PERMISSION)
                    .prefix(FileWelcomeMessage.SETTING_PREFIX)
                    .date(FileWelcomeMessage.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        WelcomeMessageModule welcomeMessage = (WelcomeMessageModule) SBPlugin.getModuleManager().getModule("WelcomeMessage");

        welcomeMessage.reload();

        sender.sendMessage(new VariableFactory(FileWelcomeMessage.MESSAGE_RELOAD)
                .prefix(FileWelcomeMessage.SETTING_PREFIX)
                .date(FileWelcomeMessage.SETTING_DATEFORMAT)
                .color()
                .toString());
    }
}