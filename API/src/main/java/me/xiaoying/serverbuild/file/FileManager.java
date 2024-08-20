package me.xiaoying.serverbuild.file;

public interface FileManager {
    File getFile(String file);

    void register(File file);

    void unregister(File file);

    void unregisterAll();

    void loads();

    void disables();

    void deletes();
}