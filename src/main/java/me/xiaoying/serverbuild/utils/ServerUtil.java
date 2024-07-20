package me.xiaoying.serverbuild.utils;

import me.xiaoying.serverbuild.ServerBuild;
import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.sql.MysqlFactory;
import me.xiaoying.sql.SqlFactory;
import me.xiaoying.sql.SqliteFactory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 工具类 服务器
 */
public class ServerUtil {
    /**
     * 判断命令发送者是否拥有权限
     *
     * @param sender 发送者
     * @param permissions 权限
     * @return 逻辑值
     */
    public static boolean hasPermission(CommandSender sender, String... permissions) {
        for (String permission : permissions) {
            if (sender.hasPermission(permission))
                continue;

            return false;
        }
        return true;
    }

    public static void sendMessage(String message) {
        sendMessage(message, true);
    }

    /**
     * 发送控制台消息
     *
     * @param message 消息内容
     * @param translate 是否使用彩色
     */
    public static void sendMessage(String message, boolean translate) {
        if (translate) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.translate(message));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }

    /**
     * 注册游戏指令
     *
     * @param command 指令
     * @param commandExecutor 指令对象
     */
    public static void registerCommand(String command, CommandExecutor commandExecutor) {
        PluginUtil.registerCommand(command, SBPlugin.getInstance());
        Objects.requireNonNull(SBPlugin.getInstance().getCommand(command)).setExecutor(commandExecutor);
    }

    /**
     * 注册游戏事件
     *
     * @param listener 事件对象
     */
    public static void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, SBPlugin.getInstance());
    }

    /**
     * 取消注册 事件
     *
     * @param listener 事件
     */
    public static void unregisterListener(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    /**
     * 获取插件运行路径
     *
     * @return 路径
     */
    public static File getDataFolder() {
        return SBPlugin.getInstance().getDataFolder();
    }

    /**
     * 执行命令
     *
     * @param command 命令
     */
    public static void dispatchCommand(String command) {
        SBPlugin.getInstance().getServer().dispatchCommand(SBPlugin.getInstance().getServer().getConsoleSender(), command);
    }

    /**
     * 获取服务器版本
     *
     * @return 服务器版本
     */
    public static String getServerVersion() {
        return SBPlugin.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    /**
     * 保存文件
     *
     * @param file 文件名称
     */
    public static void saveResources(String file) {
        SBPlugin.getInstance().saveResource(file, false);
    }


    /**
     * 获取世界
     *
     * @param world 世界名称
     * @return 世界
     */
    public static World getWorld(String world) {
        return Bukkit.getServer().getWorld(world);
    }

    /**
     * 在线玩家发送消息
     *
     * @param message 消息
     */
    public static void onlinePlayersSendMessage(String message) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> PlayerUtil.sendMessage(player, message));
    }

    /**
     * 在线玩家发送Title
     *
     * @param title 主标题
     * @param subtitle 副标题
     */
    public static void onlinePlayersSendTitle(String title, String subtitle) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> PlayerUtil.sendTitle(player, title, subtitle));
    }

    /**
     * 在线玩家发送ActionBar
     *
     * @param message 内容
     */
    public static void onlinePlayersSendActionbar(String message) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> PlayerUtil.sendActionbar(player, message));
    }

    /**
     * 获取服务器所有在线玩家
     *
     * @return 玩家列表
     */
    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getServer().getOnlinePlayers();
    }


    /**
     * 创建 Bossbar
     *
     * @param name 名称
     * @param color 颜色
     * @param style ?
     * @param healthMax 血量值
     * @return Bossbar
     */
    public static BossBar newBossBar(String name, BarColor color, BarStyle style, double healthMax) {
        BossBar bossBar = SBPlugin.getInstance().getServer().createBossBar(name, color, style);
        bossBar.setProgress(healthMax);
        return bossBar;
    }

    /**
     * 设置方块
     *
     * @param world 事件
     * @param location 坐标
     * @param blockType 方块类型
     */
    public static void setBlock(World world, Location location, String blockType) {
        Block block = world.getBlockAt(location);
        block.setType(Objects.requireNonNull(Material.getMaterial(blockType.toUpperCase())));
    }

    /**
     * 获取 NMS 包
     *
     * @param name 包名
     * @return Class
     * @throws ClassNotFoundException 抛出报错
     */
    public static Class<?> getObcClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + ServerUtil.getServerVersion() + "." + name);
    }

    /**
     * 关闭线程
     *
     * @param task task id
     */
    public static void cancelTask(int task) {
        try {
            Bukkit.getServer().getScheduler().cancelTask(task);
        } catch (Exception e) {
            // nothing
        }
    }

    /**
     * 关闭线程
     *
     * @param plugin 插件
     */
    public static void cancelTask(Plugin plugin) {
        try {
            Bukkit.getServer().getScheduler().cancelTasks(plugin);
        } catch (Exception e) {
            // nothing
        }
    }

    /**
     * 字符串转Location
     * 只可做绝对位置
     *
     * @param x X
     * @param y Y
     * @param z Z
     * @return Location
     */
    public static Location stringToLocation(String x, String y, String z) {

        return null;
    }

    /**
     * 字符串转Location
     * 可用作相对位置
     *
     * @param player 玩家
     * @param x X
     * @param y Y
     * @param z Z
     * @return Location
     */
    public static Location stringToLocation(Player player, String x, String y, String z) {

        return null;
    }

    public static SqlFactory getSqlFactory() {
        SqlFactory sqlFactory;
        switch (ConfigConstant.SETTING_DATA_TYPE.toUpperCase(Locale.ENGLISH)) {
            default:
            case "SQLITE":
                File sqlite = new File(ConfigConstant.SETTING_DATA_SQLITE_DATAPATH);
                if (!sqlite.getParentFile().exists())
                    sqlite.getParentFile().mkdirs();
                if (!sqlite.exists()) {
                    try {
                        sqlite.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                sqlFactory = new SqliteFactory(sqlite);
                break;
            case "MYSQL":
                sqlFactory = new MysqlFactory(ConfigConstant.SETTING_DATA_MYSQL_HOST,
                        ConfigConstant.SETTING_DATA_MYSQL_PORT,
                        ConfigConstant.SETTING_DATA_MYSQL_DATABASE,
                        ConfigConstant.SETTING_DATA_MYSQL_USERNAME,
                        ConfigConstant.SETTING_DATA_MYSQL_PASSWORD);
                break;
        }
        return sqlFactory;
    }
}