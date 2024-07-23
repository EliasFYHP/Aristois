package me.xiaoying.serverbuild.command.resolvelag.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.constant.ResolveLagConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class RLReloadCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(ResolveLagConstant.MESSAGE_HELP)
                .prefix(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX)
                .date(ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.rl.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(ResolveLagConstant.MESSAGE_MISSING_PERMISSION)
                    .prefix(ResolveLagConstant.SETTING_PREFIX)
                    .date(ResolveLagConstant.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        ResolveLagModule resolveLagModule = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");
        resolveLagModule.onDisable();
        resolveLagModule.disable();

        resolveLagModule.enable();

        sender.sendMessage(new VariableFactory(ResolveLagConstant.MESSAGE_RELOAD)
                .prefix(ResolveLagConstant.SETTING_PREFIX)
                .date(ResolveLagConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
    }
}