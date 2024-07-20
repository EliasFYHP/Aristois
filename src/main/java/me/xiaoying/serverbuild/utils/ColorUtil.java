package me.xiaoying.serverbuild.utils;

import org.bukkit.ChatColor;

public class ColorUtil {
    /**
     * 彩色文字
     *
     * @param text 文字
     * @return 替换后文字
     */
    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}