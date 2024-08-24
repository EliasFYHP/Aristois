package me.xiaoying.serverbuild.command.resolvelag.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.file.FileResolveLag;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Command(values = "state", length = 1)
public class RLStateCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(FileResolveLag.MESSAGE_HELP)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.rl.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_MISSING_PERMISSION)
                    .prefix(FileResolveLag.SETTING_PREFIX)
                    .date(FileResolveLag.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        World world = Bukkit.getWorld(strings[0]);
        if (world == null) {
            sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_UNKNOWN_WORLD)
                    .prefix(FileResolveLag.SETTING_PREFIX)
                    .date(FileResolveLag.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        sender.sendMessage(new VariableFactory(FileResolveLag.MESSAGE_WORLD_STATE)
                .prefix(FileResolveLag.SETTING_PREFIX)
                .date(FileResolveLag.SETTING_DATEFORMAT)
                .world(world)
                .chunks(world.getLoadedChunks().length)
                .entities(world.getEntities().size())
                .color()
                .toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String head, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1)
            Bukkit.getServer().getWorlds().forEach(world -> list.add(world.getName()));
        else if (strings.length == 2)
            list.addAll(this.getRegisteredCommands().keySet());

        List<String> conditionList = new ArrayList<>();
        for (String s : list) {
            if (!s.toUpperCase(Locale.ENGLISH).startsWith(strings[strings.length - 1].toUpperCase(Locale.ENGLISH)))
                continue;

            conditionList.add(s);
        }
        return conditionList;
    }
}