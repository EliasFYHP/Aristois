package me.xiaoying.serverbuild.script.interpreter.interpreters.playerselect;

import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PermissionSelectInterpreter implements PlayerSelect {
    @Override
    public String[] interpret(String string) {
        List<String> list;
        if (string.startsWith("!"))
            list = this.missing(StringUtil.removeSomeString(string, 0));
        else
            list = this.has(string);

        return list.toArray(new String[0]);
    }

    private List<String> has(String permission) {
        List<String> list = new ArrayList<>();
        ServerUtil.getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission(permission))
                return;

            list.add(player.getName());
        });
        return list;
    }

    private List<String> missing(String permission) {
        List<String> list = new ArrayList<>();
        ServerUtil.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission(permission))
                return;

            list.add(player.getName());
        });
        return list;
    }
}