package me.xiaoying.serverbuild.command.messageannouncer.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.MessageAnnouncerConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.module.MessageAnnouncerModule;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "reload", length = 0)
public class MAReloadCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(MessageAnnouncerConstant.MESSAGE_HELP)
                .prefix(MessageAnnouncerConstant.SETTING_PREFIX)
                .date(MessageAnnouncerConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.wm.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(MessageAnnouncerConstant.MESSAGE_MISSING_PERMISSION)
                    .prefix(MessageAnnouncerConstant.SETTING_PREFIX)
                    .date(MessageAnnouncerConstant.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        MessageAnnouncerModule messageAnnouncer = (MessageAnnouncerModule) SBPlugin.getModuleManager().getModule("MessageAnnouncer");

        messageAnnouncer.reload();

        sender.sendMessage(new VariableFactory(MessageAnnouncerConstant.MESSAGE_RELOAD)
                .prefix(MessageAnnouncerConstant.SETTING_PREFIX)
                .date(MessageAnnouncerConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
    }
}