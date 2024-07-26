package me.xiaoying.serverbuild.entity;

public class WelcomeMessageEntity {
    private final String name;
    private final int priority;
    private final String permission;
    private final String join;
    private final String quit;

    public WelcomeMessageEntity(String name, int priority, String permission, String join, String quit) {
        this.name = name;
        this.priority = priority;
        this.permission = permission;
        this.join = join;
        this.quit = quit;
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

    public String getJoin() {
        return this.join;
    }

    public String getQuit() {
        return this.quit;
    }
}