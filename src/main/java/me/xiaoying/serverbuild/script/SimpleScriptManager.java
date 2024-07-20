package me.xiaoying.serverbuild.script;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.interpreter.InterpreterManager;
import me.xiaoying.serverbuild.script.interpreter.SimpleInterpreterManager;
import me.xiaoying.serverbuild.script.scripts.ConsoleScript;
import me.xiaoying.serverbuild.script.scripts.SendScript;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.entity.Player;

import java.util.*;

public class SimpleScriptManager implements ScriptManager {
    private final InterpreterManager interpreterManager = new SimpleInterpreterManager();
    private final Map<String, Script> knownScript = new HashMap<>();

    public SimpleScriptManager() {
        this.registerScript(new SendScript());
        this.registerScript(new ConsoleScript());
    }

    @Override
    public void registerScript(Script script) {
        this.knownScript.put(script.getName().toUpperCase(Locale.ENGLISH), script);
    }

    @Override
    public void unregisterScript(String script) {
        this.knownScript.remove(script.toUpperCase(Locale.ENGLISH));
    }

    @Override
    public void unregisterScript(Script script) {
        this.knownScript.remove(script.getName().toUpperCase(Locale.ENGLISH));
    }

    @Override
    public void unregisterScripts() {
        this.knownScript.clear();
    }

    @Override
    public void performScript(String command, Player player) {
        command = new VariableFactory(command)
                .date(ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .player(player)
                .color()
                .toString();

        String[] split = command.split(" ");
        String head = split[0].toUpperCase();
        String[] functions = new ArrayList<>(Arrays.asList(split).subList(1, split.length)).toArray(new String[0]);

        if (this.knownScript.get(head) == null) {
            ServerUtil.sendMessage(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX + "&c未知命令 &e" + head + " &c，请检查命令名称");
            return;
        }

        // 解释器暂时没做

        Script script = knownScript.get(head);
        script.performCommand(player, functions);
    }

    @Override
    public List<String> getScripts() {
        List<String> list = new ArrayList<>();
        this.knownScript.values().forEach(script -> list.add(script.getName()));
        return list;
    }

    @Override
    public InterpreterManager getInterpreterManager() {
        return this.interpreterManager;
    }
}