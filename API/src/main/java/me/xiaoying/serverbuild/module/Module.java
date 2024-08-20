package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.file.File;
import me.xiaoying.serverbuild.scheduler.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private boolean opened = false;

    private final List<File> files = new ArrayList<>();
    private final List<SCommand> commands = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    private final List<Scheduler> schedulers = new ArrayList<>();

    /**
     * Get name of Module
     *
     * @return String
     */
    public abstract String getName();

    /**
     * Get alias name of Model
     *
     * @return String
     */
    public abstract String getAliasName();

    /**
     * Decide whether to enable module
     *
     * @return Boolean
     */
    public abstract boolean ready();

    /**
     * Determine whether to activated
     *
     * @return Boolean
     */
    public boolean opened() {
        return this.opened;
    }

    /**
     * Get Listeners
     *
     * @return ArrayList
     */
    public List<Listener> getListeners() {
        return this.listeners;
    }

    /**
     * Register listener
     *
     * @param listener Listener
     */
    public void registerListener(Listener listener) {
        if (this.listeners.contains(listener))
            return;

        this.listeners.add(listener);

        // register listener immediately if module opened
        if (!this.opened)
            return;

        Bukkit.getPluginManager().registerEvents(listener, SBPlugin.getInstance());
    }

    /**
     * Unregister listener
     *
     * @param listener Listener
     */
    public void unregisterListener(Listener listener) {
        if (!this.listeners.contains(listener))
            return;

        HandlerList.unregisterAll(listener);
    }

    /**
     * Unregister all listeners
     */
    public void unregisterListeners() {
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();
    }

    /**
     * Register scheduler
     *
     * @param scheduler Scheduler
     */
    public void registerScheduler(Scheduler scheduler) {
        if (this.schedulers.contains(scheduler))
            return;

        this.schedulers.add(scheduler);

        // register listener immediately if module opened
        if (!this.opened)
            return;

        scheduler.run();
    }

    /**
     * Unregister scheduler
     *
     * @param scheduler Scheduler
     */
    public void unregisterScheduler(Scheduler scheduler) {
        if (!this.schedulers.contains(scheduler))
            return;

        scheduler.stop();
        this.schedulers.remove(scheduler);
    }

    /**
     * Unregister all schedulers
     */
    public void unregisterSchedulers() {
        this.schedulers.forEach(Scheduler::stop);
        this.schedulers.clear();
    }

    /**
     * Register file
     *
     * @param file File
     */
    public void registerFile(File file) {
        if (this.files.contains(file))
            return;

        this.files.add(file);
        file.load();
    }

    /**
     * Get files of module
     *
     * @return ArrayList
     */
    public List<File> getFiles() {
        return this.files;
    }

    /**
     * Register Command
     *
     * @param command SCommand
     */
    public void registerCommand(SCommand command) {
        if (this.commands.contains(command))
            return;

        this.commands.add(command);

        // register command immediately if module opened
        if (!this.opened)
            return;

        Command annotation = command.getClass().getAnnotation(Command.class);

        if (annotation == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFined some command(" + command.getClass().getName() + ") don't use Command annotation, please check your code!"));
            return;
        }

        for (String value : annotation.values()) {
            SBPlugin.registerCommand(value, SBPlugin.getInstance());
            SBPlugin.getInstance().getCommand(value).setExecutor(command.getTabExecutor());
        }
    }

    /**
     * Unregister command
     *
     * @param command SCommand
     */
    public void unregisterCommand(SCommand command) {
        command.getValues().forEach(string -> SBPlugin.unregisterCommand(string, SBPlugin.getInstance()));
    }

    /**
     * Unregister all commands
     */
    public void unregisterCommands() {
        this.commands.forEach(this::unregisterCommand);
    }

    /**
     * Get registered commands of Module
     *
     * @return ArrayList
     */
    public List<SCommand> getCommands() {
        return this.commands;
    }

    /**
     * Initialize module
     */
    public abstract void init();

    public void enable() {
        // register listeners
        this.listeners.forEach(listener ->  Bukkit.getPluginManager().registerEvents(listener, SBPlugin.getInstance()));
        // register commands
        this.commands.forEach(command -> {
            Command annotation = command.getClass().getAnnotation(Command.class);

            if (annotation == null) {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFined some command(" + command.getClass().getName() + ") don't use Command annotation, please check your code!"));
                return;
            }

            for (String value : annotation.values()) {
                SBPlugin.registerCommand(value, SBPlugin.getInstance());
                SBPlugin.getInstance().getCommand(value).setExecutor(command.getTabExecutor());
            }
        });
        // scheduler
        this.schedulers.forEach(Scheduler::run);

        this.opened = true;
        this.onEnable();
    }

    public void disable() {
        this.onDisable();

        // unregister Scheduler
        this.schedulers.forEach(Scheduler::stop);
        this.schedulers.clear();

        // unregister listeners
        this.listeners.forEach(HandlerList::unregisterAll);
        this.listeners.clear();

        // unregister commands
        this.commands.forEach(command -> command.getValues().forEach(string -> SBPlugin.unregisterCommand(string, SBPlugin.getInstance())));
        this.commands.clear();

        // unregister files
        this.files.forEach(File::disable);
        this.files.clear();

        this.opened = false;
    }

    public void reload() {
        this.disable();

        this.init();

        if (!this.ready()) {
            this.disable();
            return;
        }

        this.enable();
    }

    public abstract void onEnable();
    public abstract void onDisable();
}