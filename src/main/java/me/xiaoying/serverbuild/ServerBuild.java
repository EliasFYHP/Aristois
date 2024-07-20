package me.xiaoying.serverbuild;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.FileConfig;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main
 */
public class ServerBuild extends JavaPlugin {
    @Override
    public void onEnable() {
        SBPlugin.setInstance(this);

        initialize();
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|感谢您使用这个插件");
        ServerUtil.sendMessage("&b|任何问题可以添加QQ: &a764932129");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
    }

    @Override
    public void onDisable() {
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
        ServerUtil.sendMessage("&b|&c插件已卸载，感谢您的使用(乌拉！");
        ServerUtil.sendMessage("&b|=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—=—>");
    }

    public static void initialize() {
        // initialize file
        FileConfig file = new FileConfig();
        SBPlugin.getFileManager().register(file);
        file.load();

        // bstats
        if (ConfigConstant.SETTING_BSTATS)
            new Metrics(SBPlugin.getInstance(), 16512);

        // SqlFactory
        SBPlugin.setSqlFactory(ServerUtil.getSqlFactory());
    }
}