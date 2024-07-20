package me.xiaoying.serverbuild.entity;

/**
 * Entity ChatFormat
 */
public class ChatFormatEntity {
    private final String name;
    private final int priority;
    private final String permission;
    private final String format;

    public ChatFormatEntity(String name, int priority, String permission, String format) {
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

    public String getFormat() {
        return this.format;
    }

    public String translate() {
        return null;
    }
}