package me.xiaoying.serverbuild.entity;

import java.util.List;

/**
 * Entity MessageAnnouncer
 */
public class MessageAnnouncerEntity {
    private final String name;
    private final List<String> scripts;

    public MessageAnnouncerEntity(String name, List<String> scripts) {
        this.name = name;
        this.scripts = scripts;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getScripts() {
        return this.scripts;
    }
}