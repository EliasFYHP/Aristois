package me.xiaoying.serverbuild.script.interpreter;

import me.xiaoying.serverbuild.script.interpreter.interpreters.PlayerSelectInterpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleInterpreterManager implements InterpreterManager {
    private List<Interpreter> interpreters = new ArrayList<>();

    public SimpleInterpreterManager() {
        this.registerInterpreter(new PlayerSelectInterpreter());
    }

    @Override
    public void registerInterpreter(Interpreter interpreter) {
        if (this.interpreters.contains(interpreter))
            return;

        this.interpreters.add(interpreter);
    }

    @Override
    public void unregisterInterpreter(Interpreter interpreter) {
        this.interpreters.remove(interpreter);
    }

    @Override
    public void unregisterInterpreters() {
        this.interpreters.clear();
    }

    @Override
    public String[] interpreter(String string) {
        List<String> list = new ArrayList<>();
        for (Interpreter interpreter : this.interpreters) {
            String[] strings;
            if ((strings = interpreter.interpret(string)) == null || strings.length == 0)
                continue;

            if (strings.length != 1 || strings[0].equalsIgnoreCase(string)) {
                List<String> list1 = new ArrayList<>(Arrays.asList(strings).subList(0, strings.length - 1));
                for (String s : strings)
                    Collections.addAll(list1, this.interpreter(s));

                strings = list1.toArray(new String[0]);
            }

            list.addAll(Arrays.asList(strings));
        }

        if (list.size() == 0)
            list.add(string);
        System.out.println(list);

        return list.toArray(new String[0]);
    }
}