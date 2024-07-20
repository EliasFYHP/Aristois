package me.xiaoying.serverbuild.entity;

import java.util.List;

/**
 * Entity ChatFormat
 */
public class ChatFormatEntity {
    private final String name;
    private final int priority;
    private final String permission;
    private final List<String> format;

    public ChatFormatEntity(String name, int priority, String permission, List<String> format) {
        this.name = name;
        this.priority = priority;
        this.permission = permission;
        this.format = format;
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getFormat() {
        return this.format;
    }

    public String translate() {
        return null;
    }
}