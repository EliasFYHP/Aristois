package me.xiaoying.serverbuild.entity;

public class ResolveLagEntity {
    private final int id;
    private final String type;
    private final String message;

    public ResolveLagEntity(int id, String type, String message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}