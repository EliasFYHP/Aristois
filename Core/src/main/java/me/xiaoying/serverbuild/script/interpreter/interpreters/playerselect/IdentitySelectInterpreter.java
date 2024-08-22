package me.xiaoying.serverbuild.script.interpreter.interpreters.playerselect;

import me.xiaoying.serverbuild.utils.ServerUtil;

import java.util.ArrayList;
import java.util.List;

public class IdentitySelectInterpreter implements PlayerSelect {
    @Override
    public String[] interpret(String string) {
        if (!string.equalsIgnoreCase("op") && !string.equalsIgnoreCase("player") && !string.equalsIgnoreCase("everyman"))
            return null;

        List<String> list = new ArrayList<>();

        if (string.equalsIgnoreCase("op"))
            list = this.op();
        if (string.equalsIgnoreCase("player"))
            list = this.player();
        if (string.equalsIgnoreCase("everyman"))
            list = this.everyman();

        return list.toArray(new String[0]);
    }

    /**
     * 管理员
     *
     * @return 管理员名称列表
     */
    private List<String> op() {
        List<String> list = new ArrayList<>();

        ServerUtil.getOnlinePlayers().forEach(player -> {
            if (!player.isOp())
                return;

            list.add(player.getName());
        });
        return list;
    }

    /**
     * 玩家
     *
     * @return 玩家名称列表
     */
    private List<String> player() {
        List<String> list = new ArrayList<>();
        ServerUtil.getOnlinePlayers().forEach(player -> list.add(player.getName()));
        return list;
    }

    /**
     * 普通玩家
     *
     * @return 普通玩家名称列表
     */
    public List<String> everyman() {
        List<String> list = new ArrayList<>();
        ServerUtil.getOnlinePlayers().forEach(player -> {
            if (player.isOp())
                return;

            list.add(player.getName());
        });
        return list;
    }
}