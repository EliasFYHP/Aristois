package me.xiaoying.serverbuild;

import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.command.serverbuild.ServerBuildCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileConfig;
import me.xiaoying.serverbuild.gui.SimpleGuiManager;
import me.xiaoying.serverbuild.module.*;
import me.xiaoying.serverbuild.script.SimpleScriptManager;
import me.xiaoying.serverbuild.utils.PluginUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Main
 */
public class ServerBuild extends JavaPlugin {
    private static final List<SCommand> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        SBPlugin.setInstance(this);
        SBPlugin.setGuiManager(new SimpleGuiManager());

        initialize();

        // bstats
        if (FileConfig.SETTING_BSTATS)
            new Metrics(SBPlugin.getInstance(), 16512);

        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|感谢您使用这个插件");
        ServerUtil.sendMessage("&b|任何问题可以添加QQ: &a764932129");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&6功能状态:");
        SBPlugin.getModuleManager().getModules().forEach(module -> {
            module.init();

            if (!module.ready()) {
                ServerUtil.sendMessage("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&c已关闭");
                module.disable();
                return;
            }
            ServerUtil.sendMessage("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&e已开启");
            module.enable();
        });
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&6全局配置状态:", true);
        if (FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE)
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &c未开启", true);
        if (FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE)
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局变量(Variable): &c未开启", true);
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
    }

    @Override
    public void onDisable() {
        unInitialize();
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&c插件已卸载，感谢您的使用(乌拉！");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
    }

    public static void initialize() {
        // initialize file
        FileConfig file = new FileConfig();
        SBPlugin.getFileManager().register(file);
        file.load();

        // init ScriptManager
        SBPlugin.setScriptManager(new SimpleScriptManager());

        // SqlFactory
        SBPlugin.setSqlFactory(ServerUtil.getSqlFactory());

        // Command
        ServerBuildCommand serverBuildCommand = new ServerBuildCommand();
        ServerBuild.commands.add(serverBuildCommand);
        ServerBuild.commands.forEach(command -> command.getValues().forEach(string -> ServerUtil.registerCommand(string, command.getTabExecutor())));

        // Module
        SBPlugin.getModuleManager().registerModule(new ChatFormatModule());
        SBPlugin.getModuleManager().registerModule(new ResolveLagModule());
        SBPlugin.getModuleManager().registerModule(new FileMonitorModule());
        SBPlugin.getModuleManager().registerModule(new WelcomeMessageModule());
        SBPlugin.getModuleManager().registerModule(new MessageAnnouncerModule());
    }

    public static void unInitialize() {
        // Command
        ServerBuild.commands.forEach(command -> command.getValues().forEach(string -> PluginUtil.unregisterCommand(string, SBPlugin.getInstance())));

        // Module
        SBPlugin.getModuleManager().disableModules();
        SBPlugin.getModuleManager().unregisterModules();

        // unregister manager
        ((SimpleGuiManager) SBPlugin.getGuiManager()).unInitialize();
    }
}