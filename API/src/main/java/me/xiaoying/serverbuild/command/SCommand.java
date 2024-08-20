package me.xiaoying.serverbuild.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Command SubCommand
 */
public abstract class SCommand {
    private final Map<String, List<RegisteredCommand>> registeredCommands = new HashMap<>();

    /**
     * Get registered commands
     *
     * @return HashMap
     */
    public Map<String, List<RegisteredCommand>> getRegisteredCommands() {
        return this.registeredCommands;
    }

    /**
     * Register new command
     *
     * @param subCommand SubCommand
     */
    public void registerCommand(SCommand subCommand) {
        Command command = subCommand.getClass().getAnnotation(Command.class);

        if (command == null) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFined some command(" + subCommand.getClass().getName() + ") don't use Command annotation, please check your code!"));
            return;
        }

        for (String s : command.values()) {
            List<RegisteredCommand> list = new ArrayList<>();
            for (int i : command.length())
                list.add(new RegisteredCommand(i, subCommand));

            this.registeredCommands.put(s, list);
        }
    }

    /**
     * Get command set value
     *
     * @return
     */
    public List<String> getValues() {
        Command command = this.getClass().getAnnotation(Command.class);
        return new ArrayList<>(Arrays.asList(command.values()).subList(0, command.values().length));
    }

    /**
     * Get command supported length
     *
     * @return ArrayList
     */
    public List<Integer> getLengths() {
        List<Integer> list = new ArrayList<>();
        for (int i : this.getClass().getAnnotation(Command.class).length())
            list.add(i);
        return list;
    }

    /**
     * Get help command for this command
     *
     * @return ArrayList
     */
    public abstract List<String> getHelpMessage();

    /**
     * Perform command
     *
     * @param sender Sender
     * @param args Command's functions
     */
    public abstract void performCommand(CommandSender sender, String[] args);

    /**
     * Perform tab<br>
     * If you don't want sender get command help message, you can override this method and return empty list
     *
     * @param sender Sender
     * @param command Command
     * @param head Command head
     * @param strings Command's functions
     * @return ArrayList
     */
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>(registeredCommands.keySet());
        if (strings.length == 1) {
            List<String> conditionList = new ArrayList<>();
            for (String s1 : list) {
                if (!s1.toUpperCase(Locale.ENGLISH).startsWith(strings[0].toUpperCase(Locale.ENGLISH)))
                    continue;
                conditionList.add(s1);
            }

            if (conditionList.size() == 0)
                return list;
            return conditionList;
        }

        List<RegisteredCommand> registeredCommand = this.registeredCommands.get(strings[0]);
        if (registeredCommand == null)
            return new ArrayList<>();

        strings = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length)).toArray(new String[0]);
        for (RegisteredCommand registeredCommand1 : registeredCommand) {
            List<String> l;
            if ((l = registeredCommand1.getSubCommand().onTabComplete(sender, command, head, strings)) == null)
                return null;

            return l;
        }
        return new ArrayList<>();
    }

    /**
     * Get bukkit TabExecutor
     *
     * @return TabExecutor
     */
    public TabExecutor getTabExecutor() {
        return new TabExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String s, @NotNull String[] strings) {
                if (!SCommand.this.getLengths().contains(strings.length)) {
                    SCommand.this.getHelpMessage().forEach(sender::sendMessage);
                    return false;
                }
                performCommand(sender, strings);
                return true;
            }

            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String s, @NotNull String[] strings) {
                return SCommand.this.onTabComplete(sender, cmd, s, strings);
            }
        };
    }
}