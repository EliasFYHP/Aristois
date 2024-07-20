package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;

/**
 * File ChatFormat.yml
 */
public class FileChatFormat extends File {
    public FileChatFormat() {
        super("ChatFormat.yml");
    }

    @Override
    public void onLoad() {
        ChatFormatConstant.ENABLE = this.getConfiguration().getBoolean("Enable");


    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}