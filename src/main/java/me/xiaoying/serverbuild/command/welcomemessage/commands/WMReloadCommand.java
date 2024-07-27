package me.xiaoying.serverbuild.command.welcomemessage.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.WelcomeMessageConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
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
        list.add(new VariableFactory(WelcomeMessageConstant.MESSAGE_HELP)
                .prefix(WelcomeMessageConstant.SETTING_PREFIX)
                .date(WelcomeMessageConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.wm.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(WelcomeMessageConstant.MESSAGE_MISSING_PERMISSION)
                    .prefix(WelcomeMessageConstant.SETTING_PREFIX)
                    .date(WelcomeMessageConstant.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        WelcomeMessageModule welcomeMessage = (WelcomeMessageModule) SBPlugin.getModuleManager().getModule("WelcomeMessage");

        welcomeMessage.reload();

        sender.sendMessage(new VariableFactory(WelcomeMessageConstant.MESSAGE_RELOAD)
                .prefix(WelcomeMessageConstant.SETTING_PREFIX)
                .date(WelcomeMessageConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
    }
}