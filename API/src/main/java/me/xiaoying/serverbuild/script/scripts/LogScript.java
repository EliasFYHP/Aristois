package me.xiaoying.serverbuild.script.scripts;

import me.xiaoying.serverbuild.script.Script;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Script log
 */
public class LogScript implements Script {
    @Override
    public String getName() {
        return "log";
    }

    @Override
    public void performCommand(CommandSender sender, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }

        Bukkit.getServer().getConsoleSender().sendMessage(stringBuilder.toString());
    }

    @Override
    public boolean processFirst() {
        return false;
    }
}