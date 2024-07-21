package me.xiaoying.serverbuild.command.chatformat;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.RegisteredCommand;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.chatformat.commands.ChatFormatReloadCommand;
import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.factory.VariableFactory;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(values = {"cf", "chatforamt"}, length = {1, 3, 4})
public class ChatFormatCommand extends SCommand {
    public ChatFormatCommand() {
        this.registerCommand(new ChatFormatReloadCommand());
    }

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
        // 判断是否存在相应命令
        String head = strings[0];
        if (!this.getRegisteredCommands().containsKey(head)) {
            this.getHelpMessage().forEach(sender::sendMessage);
            return;
        }

        // 移除 head
        List<String> list = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
        strings = list.toArray(new String[0]);

        boolean isDo = false;
        for (RegisteredCommand registeredCommand : this.getRegisteredCommands().get(head)) {
            if (registeredCommand.getLength() != strings.length)
                continue;

            registeredCommand.getSubCommand().performCommand(sender, strings);
            isDo = true;
        }

        if (isDo)
            return;

        // 未执行则发出帮助信息
        this.getHelpMessage().forEach(sender::sendMessage);
    }
}