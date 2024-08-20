package me.xiaoying.serverbuild.script;

import me.xiaoying.serverbuild.script.interpreter.InterpreterManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Script manager
 */
public interface ScriptManager {
    void registerScript(Script script);

    void unregisterScript(String script);

    void unregisterScript(Script script);

    void unregisterScripts();

    void performScript(String command, CommandSender sender);

    List<String> getScripts();

    InterpreterManager getInterpreterManager();
}