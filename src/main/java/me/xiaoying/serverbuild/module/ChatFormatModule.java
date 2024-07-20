package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.entity.ChatFormatEntity;
import me.xiaoying.serverbuild.file.FileChatFormat;
import me.xiaoying.serverbuild.listener.ChatFormatListener;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.*;

/**
 * Module ChatFormat
 */
public class ChatFormatModule extends Module {
    private final Map<String, ChatFormatEntity> entityMap = new HashMap<>();

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
        FileChatFormat file = new FileChatFormat();
        this.registerFile(file);
        YamlUtil.getNodes(file.getFile().getPath(), "Formats").forEach(object -> {
            String string = object.toString();
            ChatFormatEntity entity = new ChatFormatEntity(string,
                    file.getConfiguration().getInt("Formats." + string + ".Priority"),
                    file.getConfiguration().getString("Formats." + string + ".Permission"),
                    file.getConfiguration().getString("Formats." + string + ".Format"));
            this.entityMap.put(string.toUpperCase(Locale.ENGLISH), entity);
        });

        // register listeners
        this.registerListener(new ChatFormatListener());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public ChatFormatEntity getChatFormatEntity(String name) {
        return this.entityMap.get(name.toUpperCase(Locale.ENGLISH));
    }

    public List<ChatFormatEntity> getChatFormatEntities() {
        return new ArrayList<>(this.entityMap.values());
    }
}