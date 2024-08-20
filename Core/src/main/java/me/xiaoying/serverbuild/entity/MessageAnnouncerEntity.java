package me.xiaoying.serverbuild.entity;

import java.util.List;

/**
 * Entity MessageAnnouncer
 */
public class MessageAnnouncerEntity {
    private final String name;
    private final String permission;
    private final List<String> scripts;

    public MessageAnnouncerEntity(String name, String permission, List<String> scripts) {
        this.name = name;
        this.permission = permission;
        this.scripts = scripts;
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getScripts() {
        return this.scripts;
    }
}