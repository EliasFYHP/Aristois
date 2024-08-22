package me.xiaoying.serverbuild.script.interpreter.interpreters;

import me.xiaoying.serverbuild.script.interpreter.Interpreter;
import me.xiaoying.serverbuild.script.interpreter.interpreters.playerselect.IdentitySelectInterpreter;
import me.xiaoying.serverbuild.script.interpreter.interpreters.playerselect.PermissionSelectInterpreter;
import me.xiaoying.serverbuild.script.interpreter.interpreters.playerselect.PlayerSelect;
import me.xiaoying.serverbuild.utils.ServerUtil;
import me.xiaoying.serverbuild.utils.StringUtil;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Interpreter select player
 */
public class PlayerSelectInterpreter implements Interpreter {
    private final Map<String, PlayerSelect> knownInterpreters = new HashMap<>();

    public PlayerSelectInterpreter() {
        this.knownInterpreters.put("identity", new IdentitySelectInterpreter());
        this.knownInterpreters.put("permission", new PermissionSelectInterpreter());
    }

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
        }

        if (list.isEmpty())
            list.add(string);

        List<String> newList = new ArrayList<>();
        for (String s : list) {
            String[] split = s.split(" ");
            parameter: for (int i = 0; i < split.length; i++) {
                if (!split[i].startsWith("*{"))
                    continue;

                String s1 = split[i];
                while (StringUtil.getKeySize(s1, "{", true) > StringUtil.getKeySize(s1, "}", true)) {
                    // ++i 避免for下次依旧循环此段字符串
                    s1 = s1 + split[++i];
                    if (i + 1 > split.length)
                        break parameter;
                }

                List<String> players = new ArrayList<>();

                // remove '*{' and '}'
                s1 = StringUtil.removeSomeString(s1, 0);
                s1 = StringUtil.removeSomeString(s1, 0);
                s1 = StringUtil.removeSomeString(s1, s1.length() - 1);

                // ergodic parameters
                for (String group : s1.split(",")) {
                    group = group.replace(" ", "");

                    if (!group.contains("="))
                        continue;

                    String[] groupSplit = group.split("=");
                    String name = groupSplit[0];
                    String value = groupSplit[1];

                    List<String> interpret = new ArrayList<>(Arrays.asList(this.knownInterpreters.get(name).interpret(value)));
                    if (players.isEmpty())
                        players = interpret;

                    if (interpret.isEmpty())
                        continue;

                    List<String> filter = new ArrayList<>();
                    for (String s2 : interpret) {
                        if (!players.contains(s2))
                            continue;

                        filter.add(s2);
                    }

                    players = filter;
                }

                for (String player : players)
                    newList.add(s.replaceFirst("\\*\\{" + s1 + "}", player));
            }
        }
        return newList.isEmpty() ? list.toArray(new String[0]) : newList.toArray(new String[0]);
    }
}