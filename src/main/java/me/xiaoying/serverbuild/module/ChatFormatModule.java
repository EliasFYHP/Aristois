package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.listener.ChatFormatListener;

/**
 * Module ChatFormat
 */
public class ChatFormatModule extends Module {
    @Override
    public String getName() {
        return "聊天格式";
    }

    @Override
    public String getAliasName() {
        return "ChatFormat";
    }

    @Override
    public boolean ready() {
        return ChatFormatConstant.ENABLE;
    }

    @Override
    public void init() {
        // register files
        this.registerFile(new FileChatFormat());

        // register listeners
        this.registerListener(new ChatFormatListener());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}