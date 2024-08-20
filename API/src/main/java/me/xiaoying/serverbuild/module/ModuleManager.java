package me.xiaoying.serverbuild.module;

import java.util.List;

public interface ModuleManager {
    void registerModule(Module module);

    void unregisterModule(Module module);

    void unregisterModules();

    Module getModule(String name);

    List<Module> getModules();

    void enableModules();

    void disableModules();
}