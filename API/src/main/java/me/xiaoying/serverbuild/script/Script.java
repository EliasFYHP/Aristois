package me.xiaoying.serverbuild.script;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Script
 */
public interface Script {
    String getName();

    void performCommand(CommandSender sender, String[] args);

    boolean processFirst();
}