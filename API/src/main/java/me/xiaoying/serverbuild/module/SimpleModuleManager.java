package me.xiaoying.serverbuild.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleModuleManager implements ModuleManager {
    private final List<Module> knownModules = new ArrayList<>();

    @Override
    public void registerModule(Module module) {
        if (this.knownModules.contains(module))
            return;

        this.knownModules.add(module);
    }

    @Override
    public void unregisterModule(Module module) {
        if (!this.knownModules.contains(module))
            return;
        module.onDisable();
        module.disable();
        this.knownModules.remove(module);
    }

    @Override
    public void unregisterModules() {
        this.disableModules();
        this.knownModules.clear();
    }

    @Override
    public Module getModule(String name) {
        for (Module knownModule : this.knownModules) {
            if (!knownModule.getAliasName().equalsIgnoreCase(name))
                continue;

            return knownModule;
        }
        return null;
    }

    @Override
    public List<Module> getModules() {
        return this.knownModules;
    }

    @Override
    public void enableModules() {
        this.knownModules.forEach(Module::enable);
    }

    @Override
    public void disableModules() {
        this.knownModules.forEach(module -> {
            module.onDisable();
            module.disable();
        });
    }
}