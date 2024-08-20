package me.xiaoying.serverbuild.script.interpreter;

/**
 * Interpreter manager
 */
public interface InterpreterManager {
    /**
     * Register interpreter
     *
     * @param interpreter Interpreter
     */
    void registerInterpreter(Interpreter interpreter);

    /**
     * Unregister interpreter
     *
     * @param interpreter Interpreter
     */
    void unregisterInterpreter(Interpreter interpreter);

    /**
     * Unregister all Interpreter
     */
    void unregisterInterpreters();

    /**
     * Interpreter string
     *
     * @param string String
     * @return String[]
     */
    String[] interpreter(String string);
}