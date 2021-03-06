package demo.elastic.search.engine.script.impl;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.engine.script.ExecuteScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;


/**
 * js引擎
 */
@Slf4j
@Component
public class JavaScriptExecuteScript implements ExecuteScript {

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
    public JSONObject eval(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String function = "function wash(source){ var dataMap = JSON.parse(source); " + script + ";return dataMap;}";
        if (engine instanceof Invocable) {
            engine.eval(function);
            Invocable invokeEngine = (Invocable) engine;
            Object o = invokeEngine.invokeFunction("wash", JSONObject.toJSON(source).toString());
            return JSONObject.parseObject(JSONObject.toJSON(o).toString());
        } else {
            log.error("这个脚本引擎不支持动态调用:{}", script);
        }
        return null;
    }

    /**
     * 清洗
     * 1.完成脚本执行
     * 2.如果返回true -> 保留 否则false
     * <p>
     * 注意:默认返回 false
     *
     * @param script
     * @param source
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public boolean evalAndFilter(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String function = "function wash(source){ var dataMap = JSON.parse(source); " + script + ";return false;}";
        if (engine instanceof Invocable) {
            engine.eval(function);
            Invocable invokeEngine = (Invocable) engine;
            Object o = invokeEngine.invokeFunction("wash", JSONObject.toJSON(source).toString());
            if ("false".equalsIgnoreCase(o.toString())) {
                return false;
            } else {
                /**
                 * 返回true -> 代表数据可用
                 *
                 * 把json回转为map
                 */
                JSONObject object = JSONObject.parseObject(JSONObject.toJSON(o).toString());
                source.clear();
                source.putAll(object);
                return true;
            }

        } else {
            log.error("这个脚本引擎不支持动态调用:{}", script);
        }
        return false;
    }

    /**
     * 清洗
     * 1.完成脚本执行
     * 2.如果返回true -> 保留 否则false
     * <p>
     * 注意:默认返回 false
     *
     * @param script
     * @param source
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public static boolean evalAndFilter2(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String function = "function wash(source){ var dataMap = JSON.parse(source); " + script + ";return false;}";
        if (engine instanceof Invocable) {
            engine.eval(function);
            Invocable invokeEngine = (Invocable) engine;
            Object o = invokeEngine.invokeFunction("wash", JSONObject.toJSON(source).toString());
            if ("false".equalsIgnoreCase(o.toString())) {
                return false;
            } else {
                /**
                 * 返回true -> 代表数据可用
                 *
                 * 把json回转为map
                 */
                JSONObject object = JSONObject.parseObject(JSONObject.toJSON(o).toString());
                source.clear();
                source.putAll(object);
                return true;
            }

        } else {
            log.error("这个脚本引擎不支持动态调用:{}", script);
        }
        return false;
    }
}
