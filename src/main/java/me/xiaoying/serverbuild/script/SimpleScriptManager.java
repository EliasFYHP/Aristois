package me.xiaoying.serverbuild.script;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.script.interpreter.InterpreterManager;
import me.xiaoying.serverbuild.script.interpreter.SimpleInterpreterManager;
import me.xiaoying.serverbuild.script.scripts.ConsoleScript;
import me.xiaoying.serverbuild.script.scripts.LogScript;
import me.xiaoying.serverbuild.script.scripts.SendScript;
import me.xiaoying.serverbuild.script.scripts.TitleScript;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class SimpleScriptManager implements ScriptManager {
    private final InterpreterManager interpreterManager = new SimpleInterpreterManager();
    private final Map<String, Script> knownScript = new HashMap<>();

    public SimpleScriptManager() {
        this.registerScript(new LogScript());
        this.registerScript(new SendScript());
        this.registerScript(new TitleScript());
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
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SBPlugin.getInstance(), () -> callScript(command, player));
    }

    private void callScript(String command, Player player) {
        command = new VariableFactory(command)
                .date(ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT)
                .prefix(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX)
                .player(player)
                .placeholder(player)
                .color()
                .toString();
        if (command.endsWith("§r")) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] split = command.split("");
            for (int i = 0; i < split.length; i++) {
                if (i == split.length - 2)
                    break;

                stringBuilder.append(split[i]);
            }
            command = stringBuilder.toString();
        }

        String[] split = command.split(" ");
        String head = split[0].toUpperCase();

        if (this.knownScript.get(head) == null) {
            // interpreter
            String[] strings = this.getInterpreterManager().interpreter(command);
            if (strings == null || strings.length == 0)
                return;

            if (strings.length == 1 && strings[0].equalsIgnoreCase(command)) {
                ServerUtil.sendMessage(ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX + "&c未知命令 &e" + head + " &c，请检查命令名称");
                return;
            }

            for (String string : strings)
                this.callScript(string, player);
            return;
        }

        Script script = this.knownScript.get(head);
        String[] functions = new ArrayList<>(Arrays.asList(split).subList(1, split.length)).toArray(new String[0]);

        // process first
        if (script.processFirst()) {
            script.performCommand(player, functions);
            return;
        }

        // interpreter
        String[] strings = this.getInterpreterManager().interpreter(command);
        if (strings == null || strings.length == 0)
            return;

        if (strings.length == 1 && strings[0].equalsIgnoreCase(command)) {
            script.performCommand(player, functions);
            return;
        }

        for (String string : strings)
            this.callScript(string, player);
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