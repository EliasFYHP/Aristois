package me.xiaoying.serverbuild.command;

/**
 * Command RegisteredCommand
 */
public class RegisteredCommand {
    int length;
    SCommand subCommand;

    public RegisteredCommand(int length, SCommand subCommand) {
        this.length = length;
        this.subCommand = subCommand;
    }

    public int getLength() {
        return this.length;
    }

    public SCommand getSubCommand() {
        return this.subCommand;
    }
}