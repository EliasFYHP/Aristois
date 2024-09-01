package me.xiaoying.serverbuild.core;

import me.xiaoying.serverbuild.file.FileManager;
import me.xiaoying.serverbuild.file.SimpleFileManager;
import me.xiaoying.serverbuild.gui.GuiManager;
import me.xiaoying.serverbuild.module.ModuleManager;
import me.xiaoying.serverbuild.module.SimpleModuleManager;
import me.xiaoying.serverbuild.script.ScriptManager;
import me.xiaoying.sql.SqlFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class SBPlugin {
    private static JavaPlugin instance;
    private static ScriptManager scriptManager;
    private static GuiManager guiManager;
    private static FileManager fileManager;
    private static ModuleManager moduleManager;
    private static SqlFactory sqlFactory;

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static void setInstance(JavaPlugin plugin) {
        instance = plugin;

        SBPlugin.guiManager = new GuiManager();
        SBPlugin.fileManager = new SimpleFileManager();
        SBPlugin.moduleManager = new SimpleModuleManager();
    }

    public static ScriptManager getScriptManager() {
        return scriptManager;
    }

    public static void setScriptManager(ScriptManager scriptManager) {
        if (SBPlugin.scriptManager != null)
            return;

        SBPlugin.scriptManager = scriptManager;
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    public static void setModuleManager(ModuleManager moduleManager) {
        if (SBPlugin.moduleManager != null)
            return;

        SBPlugin.moduleManager = moduleManager;
    }

    public static GuiManager getGuiManager() {
        return SBPlugin.guiManager;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static void setFileManager(FileManager fileManager) {
        if (SBPlugin.fileManager != null)
            return;

        SBPlugin.fileManager = fileManager;
    }

    /**
     * Get SqlFactory
     *
     * @return SqlFactory
     */
    public static SqlFactory getSqlFactory() {
        return sqlFactory;
    }

    /**
     * Set SqlFactory
     *
     * @param sqlFactory SqlFactory
     */
    public static void setSqlFactory(SqlFactory sqlFactory) {
        SBPlugin.sqlFactory = sqlFactory;
    }

    /**
     * unregister command
     *
     * @param command Command
     * @param plugin Plugin
     */
    public static void unregisterCommand(String command, Plugin plugin) {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> commands = (Map<String, Command>) knownCommandsField.get(commandMap);
            Iterator<Map.Entry<String, Command>> it = commands.entrySet().iterator();
            while (it.hasNext()) {
                PluginCommand c;
                Map.Entry<String, Command> entry = it.next();
                if (!(entry.getValue() instanceof PluginCommand) ||
                        (c = (PluginCommand)entry.getValue()).getPlugin() != plugin ||
                        !((PluginCommand) entry.getValue()).getName().equalsIgnoreCase(command))
                    continue;
                c.unregister(commandMap);
                it.remove();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Register command
     *
     * @param command Command
     * @param plugin Plugin
     */
    public static void registerCommand(String command, Plugin plugin) {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer().getPluginManager());
            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            Map<String, Command> commands = (Map) knownCommandsField.get(commandMap);
            for (Map.Entry<String, Command> stringCommandEntry : commands.entrySet()) {
                if (!(stringCommandEntry.getValue() instanceof PluginCommand))
                    continue;

                PluginCommand cmd = (PluginCommand) stringCommandEntry.getValue();
                if (cmd.getPlugin() == plugin && cmd.getName().equalsIgnoreCase(command))
                    return;
            }

            Class<PluginCommand> clazz = PluginCommand.class;
            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PluginCommand pluginCommand = (PluginCommand) constructor.newInstance(command, plugin);
            pluginCommand.register(commandMap);
            ((Map<String, Command>) knownCommandsField.get(commandMap)).put(command, pluginCommand);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}