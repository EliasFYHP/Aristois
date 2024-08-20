package me.xiaoying.serverbuild.utils;

import me.xiaoying.serverbuild.core.SBPlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 工具类 玩家
 */
public class PlayerUtil {
    /**
     * 判断玩家是否拥有指定权限
     *
     * @param player 玩家
     * @param permissions 权限
     * @return 逻辑值
     */
    public static boolean hasPermission(Player player, String... permissions) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission))
                continue;

            return true;
        }
        return false;
    }

    /**
     * 发送消息
     *
     * @param player 玩家
     * @param message 消息
     */
    public static void sendMessage(Player player, String message) {
        player.sendMessage(ColorUtil.translate(message));
    }

    /**
     * 发送Title
     *
     * @param player 玩家
     * @param title 主标题
     * @param subtitle 副标题
     */
    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, title, subtitle, 10, 70, 20);
    }

    /**
     * 发送Title
     *
     * @param player 玩家
     * @param title 主标题
     * @param subtitle 副标题
     * @param in 淡入时间
     * @param stay 停留时间
     * @param out 淡出时间
     */
    public static void sendTitle(Player player, String title, String subtitle, int in, int stay, int out) {
        player.sendTitle(title, subtitle, in, stay, out);
    }

    /**
     * 发送Actionbar
     *
     * @param player 玩家
     * @param message 内容
     * @param second 停留时间
     */
    public static void sendActionbar(Player player, String message, int second) {
        int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(SBPlugin.getInstance(), () -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message)), 0L, 20L);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(second);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
            Bukkit.getScheduler().cancelTask(i);
        });
    }

    /**
     * 发送Actionbar
     *
     * @param player 玩家
     * @param message 内容
     */
    public static void sendActionbar(Player player, String message) {
        sendActionbar(player, message, 3);
    }
}