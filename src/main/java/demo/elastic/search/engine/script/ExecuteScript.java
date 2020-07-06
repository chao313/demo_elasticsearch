package demo.elastic.search.engine.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

import static java.lang.System.err;


/**
 * js引擎
 */
public class ExecuteScript {

    public static Object eval(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String function = "function wash(dataMap){" + script + ";return dataMap;}";
        if (engine instanceof Invocable) {
            engine.eval(function);
            Invocable invokeEngine = (Invocable) engine;
            Object o = invokeEngine.invokeFunction("wash", source);
            return o;
        } else {
            err.println("这个脚本引擎不支持动态调用");
        }
        return null;
    }
}
