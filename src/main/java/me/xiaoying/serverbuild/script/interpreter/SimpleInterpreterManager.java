package me.xiaoying.serverbuild.script.interpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleInterpreterManager implements InterpreterManager {
    private List<Interpreter> interpreters = new ArrayList<>();

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
        for (Interpreter interpreter : this.interpreters) {
            String[] strings;
            if ((strings = interpreter.interpret(string)) == null || strings.length == 0)
                return new String[0];

            if (strings.length != 1 || strings[0].equalsIgnoreCase(string)) {
                List<String> list = new ArrayList<>(Arrays.asList(strings).subList(0, strings.length - 1));
                for (String s : strings)
                    Collections.addAll(list, this.interpreter(s));

                strings = list.toArray(new String[0]);
            }

            return strings;
        }

        return new String[] {string};
    }
}