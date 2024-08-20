package me.xiaoying.serverbuild.file;

import java.util.ArrayList;
import java.util.List;

public class SimpleFileManager implements FileManager {
    private final List<File> knownFiles = new ArrayList<>();

    @Override
    public File getFile(String file) {
        for (File knownFile : this.knownFiles) {
            if (!knownFile.getName().equalsIgnoreCase(file))
                continue;

            return knownFile;
        }
        return null;
    }

    @Override
    public void register(File file) {
        if (this.knownFiles.contains(file))
            return;

        this.knownFiles.add(file);
    }

    @Override
    public void unregister(File file) {
         if (!this.knownFiles.contains(file))
             return;

         file.onDisable();
         this.knownFiles.remove(file);
    }

    @Override
    public void unregisterAll() {
        this.knownFiles.forEach(File::onDisable);
    }

    @Override
    public void loads() {
        this.knownFiles.forEach(file -> {
            file.load();
            file.onLoad();
        });
    }

    @Override
    public void disables() {
        this.knownFiles.forEach(File::onDisable);
    }

    @Override
    public void deletes() {
        this.knownFiles.forEach(file -> {
            file.delete();
            file.onDelete();
        });
    }
}