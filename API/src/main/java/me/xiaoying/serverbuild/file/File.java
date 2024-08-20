package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.core.SBPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class File {
    private final java.io.File file;
    private YamlConfiguration configuration;

    public File(String file) {
        this.file = new java.io.File(SBPlugin.getInstance().getDataFolder(), file);
    }

    public File(String path, String name) {
//        java.io.File dataFolder = new java.io.File(path);
//        if (!dataFolder.exists())
//            dataFolder.mkdirs();
//
//        if (!dataFolder.isDirectory())
//            throw new RuntimeException("\"" + path + "\" is not a directory");
//
//        this.file = new java.io.File(path, name);
        this.file = new java.io.File(path, name);
    }

    public java.io.File getParent() {
        return this.file.getParentFile();
    }

    public String getName() {
        return this.file.getName();
    }

    public java.io.File getFile() {
        return this.file;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void load() {
        if (!this.file.exists()) SBPlugin.getInstance().saveResource(this.file.getName(), false);
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        this.onLoad();
    }

    public void delete() {
        this.file.delete();
        this.onDelete();
    }

    public void disable() {
        this.onDisable();
    }

    public abstract void onLoad();
    public abstract void onDelete();
    public abstract void onDisable();
}