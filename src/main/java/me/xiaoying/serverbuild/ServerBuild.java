package me.xiaoying.serverbuild;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileConfig;
import me.xiaoying.serverbuild.module.ChatFormatModule;
import me.xiaoying.serverbuild.script.SimpleScriptManager;
import me.xiaoying.serverbuild.utils.ServerUtil;
//import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main
 */
public class ServerBuild extends JavaPlugin {
    @Override
    public void onEnable() {
        SBPlugin.setInstance(this);

        initialize();
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|感谢您使用这个插件");
        ServerUtil.sendMessage("&b|任何问题可以添加QQ: &a764932129");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&6功能状态:");
        SBPlugin.getModuleManager().getModules().forEach(module -> {
            module.init();

            if (!module.ready()) {
                ServerUtil.sendMessage("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&c已关闭");
                return;
            }
            ServerUtil.sendMessage("&b|&r    &a" + module.getName() + "(" + module.getAliasName() + "): " + "&e已开启");
            module.enable();
        });
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&6全局配置状态:", true);
        if (ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE)
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &e已开启", true);
        else
            ServerUtil.sendMessage("&b|    &a全局词条(Message): &c未开启", true);
        if (ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE)
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

        // bstats
//        if (ConfigConstant.SETTING_BSTATS)
//            new Metrics(SBPlugin.getInstance(), 16512);

        // SqlFactory
        SBPlugin.setSqlFactory(ServerUtil.getSqlFactory());

        // Module
        SBPlugin.getModuleManager().registerModule(new ChatFormatModule());
    }

    public static void unInitialize() {
        SBPlugin.getModuleManager().disableModules();
    }
}