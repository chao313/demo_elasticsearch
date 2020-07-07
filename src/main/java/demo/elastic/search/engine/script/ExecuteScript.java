package demo.elastic.search.engine.script;

import com.alibaba.fastjson.JSONObject;

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

    /**
     * dataMap['xx'] = typeof(dataMap)
     * dataMap['xy'] = (dataMap instanceof Array)
     * dataMap['xz'] = delete dataMap['xy']
     * <p>
     * 注意:
     * 这里的map传入js内部的时候,似乎只能增加和修改(不能删除???)
     * -> 采用了折中的方式 java的map转json字符串 由js转换为json,处理后返回的数据转换层JSONObject
     *
     * @param script
     * @param source
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public static JSONObject eval(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String function = "function wash(source){ var dataMap = JSON.parse(source); " + script + ";return dataMap;}";
        if (engine instanceof Invocable) {
            engine.eval(function);
            Invocable invokeEngine = (Invocable) engine;
            Object o = invokeEngine.invokeFunction("wash", JSONObject.toJSON(source).toString());
            return JSONObject.parseObject(JSONObject.toJSON(o).toString());
        } else {
            err.println("这个脚本引擎不支持动态调用");
        }
        return null;
    }
}
