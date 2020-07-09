package demo.elastic.search.engine.script;

import com.alibaba.fastjson.JSONObject;

import javax.script.ScriptException;
import java.util.Map;

public interface ExecuteScript {

    /**
     * 要求:传入Map数据,JS脚本处理后,转换成JSON返回
     *
     * @param script 脚本
     * @param source 待处理数据
     * @return 返回的是JSON格式的数据
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    JSONObject eval(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException;

    /**
     * 要求:传入Map数据,JS脚本处理后,如果JS返回bool返回给java，map数据已经修改过
     *
     * @param script 脚本
     * @param source 待处理数据
     * @return 返回的是JSON格式的数据
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    boolean evalAndFilter(String script, Map<String, Object> source) throws ScriptException, NoSuchMethodException;
}
