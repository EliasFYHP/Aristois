package me.xiaoying.serverbuild.command.resolvelag.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileResolveLag;
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
        list.add(new VariableFactory(FileResolveLag.MESSAGE_HELP)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.rl.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_MISSING_PERMISSION)
                    .prefix(FileResolveLag.SETTING_PREFIX)
                    .date(FileResolveLag.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        ResolveLagModule resolveLagModule = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");

        resolveLagModule.reload();

        sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_RELOAD)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .color()
                .toString());
    }
}