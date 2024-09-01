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

    /**
     * Set plugin instance
     *
     * @param plugin JavaPlugin
     */
    public static void setInstance(JavaPlugin plugin) {
        instance = plugin;

        SBPlugin.fileManager = new SimpleFileManager();
        SBPlugin.moduleManager = new SimpleModuleManager();
    }

    /**
     * Get manager of script
     *
     * @return ScriptManager
     */
    public static ScriptManager getScriptManager() {
        return scriptManager;
    }

    /**
     * Set manager of script
     *
     * @param scriptManager ScriptManager
     */
    public static void setScriptManager(ScriptManager scriptManager) {
        if (SBPlugin.scriptManager != null)
            return;

        SBPlugin.scriptManager = scriptManager;
    }

    /**
     * Get manager of module
     *
     * @return
     */
    public static ModuleManager getModuleManager() {
        return moduleManager;
    }

    /**
     * Set manager of module
     *
     * @param moduleManager ModuleManager
     */
    public static void setModuleManager(ModuleManager moduleManager) {
        if (SBPlugin.moduleManager != null)
            return;

        SBPlugin.moduleManager = moduleManager;
    }

    /**
     * Get gui manager
     *
     * @return GuiManager
     */
    public static GuiManager getGuiManager() {
        return SBPlugin.guiManager;
    }

    /**
     * Set manager of gui
     *
     * @param guiManager GuiManager
     */
    public static void setGuiManager(GuiManager guiManager) {
        SBPlugin.guiManager = guiManager;
    }

    /**
     * Get manager of file
     *
     * @return FileManager
     */
    public static FileManager getFileManager() {
        return fileManager;
    }

    /**
     * Set manager of file
     *
     * @param fileManager FileManager
     */
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