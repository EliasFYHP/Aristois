package me.xiaoying.serverbuild.script.interpreter.interpreters;

import me.xiaoying.serverbuild.script.interpreter.Interpreter;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Interpreter select player
 */
public class PlayerSelectInterpreter implements Interpreter {
    @Override
    public String[] interpret(String string) {
        List<String> list = new ArrayList<>();

        if (string.contains(" * ")) {
            String[] split = string.split(" \\* ");
            for (Player onlinePlayer : ServerUtil.getOnlinePlayers()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    stringBuilder.append(split[i]);

                    if (i == split.length - 1)
                        break;

                    stringBuilder.append(" ").append(onlinePlayer.getName());

                    if (i < split.length - 1)
                        stringBuilder.append(" ");
                }
                list.add(stringBuilder.toString());
            }

            return list.toArray(new String[0]);
        }

        return new String[0];
    }
}