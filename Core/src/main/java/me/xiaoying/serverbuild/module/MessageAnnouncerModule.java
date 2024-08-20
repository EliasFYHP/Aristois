package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.entity.MessageAnnouncerEntity;
import me.xiaoying.serverbuild.file.FileMessageAnnouncer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Module MessageAnnouncer
 */
public class MessageAnnouncerModule extends Module {
    private final Map<String, MessageAnnouncerEntity> announcers = new HashMap<>();

    @Override
    public String getName() {
        return "自动公告";
    }

    @Override
    public String getAliasName() {
        return "MessageAnnouncer";
    }

    @Override
    public boolean ready() {
        return false;
    }

    @Override
    public void init() {
        // register files
        this.registerFile(new FileMessageAnnouncer());


    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}