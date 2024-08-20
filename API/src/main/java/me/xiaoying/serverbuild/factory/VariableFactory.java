package me.xiaoying.serverbuild.factory;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Factory Variable
 */
public class VariableFactory {
    private String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory amount(int amount) {
        this.string = this.string.replace("%amount%", String.valueOf(amount));
        return this;
    }

    public VariableFactory chunks(int chunks) {
        this.string = this.string.replace("%chunks%", String.valueOf(chunks));
        return this;
    }

    public VariableFactory entities(int entities) {
        this.string = this.string.replace("%entities%", String.valueOf(entities));
        return this;
    }

    public VariableFactory color() {
        this.string = ChatColor.translateAlternateColorCodes('&', this.string);
        return this;
    }

    public VariableFactory date(String format) {
        this.string = this.string.replace("%date%", new SimpleDateFormat(format).format(new Date()));
        return this;
    }

    public VariableFactory message(String message) {
        this.string = this.string.replace("%message%", message);
        return this;
    }

    public VariableFactory placeholder(Player player) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
            return this;

        this.string = PlaceholderAPI.setPlaceholders(player, this.string);
        return this;
    }

    public VariableFactory player(Player player) {
        return this.player(player.getDisplayName());
    }

    public VariableFactory player(String player) {
        this.string = this.string.replace("%player%", player);
        return this;
    }

    public VariableFactory prefix(String prefix) {
        this.string = this.string.replace("%prefix%", prefix);
        return this;
    }

    public VariableFactory time(int time) {
        return this.time(String.valueOf(time));
    }

    public VariableFactory time(long time) {
        return this.time(String.valueOf(time));
    }

    public VariableFactory time(String time) {
        this.string = this.string.replace("%time%", time);
        return this;
    }

    public VariableFactory world(String world) {
        this.string = this.string.replace("%world%", world);
        return this;
    }

    public VariableFactory world(World world) {
        return this.world(world.getName());
    }

    @Override
    public String toString() {
        return this.string;
    }
}