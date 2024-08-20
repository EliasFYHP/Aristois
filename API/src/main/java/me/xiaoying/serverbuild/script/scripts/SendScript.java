package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.script.Script;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Script send
 */
public class SendScript implements Script {
    @Override
    public String getName() {
        return "send";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        CommandSender findSender;

        if (args.length == 0) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c错误的命令格式，应当为 &esend [player] 内容"));
            return;
        }

        if ((findSender = Bukkit.getServer().getPlayer(args[0])) == null)
            findSender = sender;

        for (int i = 0; i < args.length; i++) {
            if (i == 0)
                continue;

            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }

        findSender.sendMessage(stringBuilder.toString());
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}