package me.xiaoying.serverbuild.factory;

import me.xiaoying.serverbuild.utils.DateUtil;
import org.bukkit.entity.Player;

/**
 * Factory Variable
 */
public class VariableFactory {
    private String string;

    public VariableFactory(String string) {
        this.string = string;
    }

    public VariableFactory date(String format) {
        this.string = this.string.replace("%date%", DateUtil.getDate(format));
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

    public VariableFactory time(String time) {
        this.string = this.string.replace("%time%", time);
        return this;
    }

    @Override
    public String toString() {
        return this.string;
    }
}