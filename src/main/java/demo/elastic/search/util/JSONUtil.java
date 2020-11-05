package demo.elastic.search.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自定义json处理
 */
@Slf4j
@Service
public class JSONUtil {

    /**
     * @param root
     * @param level     key所在的级别
     * @param fieldName 要获取的value的key
     * @param <T>
     * @return 注意:
     * 这里返回前会判断 -> 把递归多个返回值的情况下归为1条路径即可!!!
     */
    public static <T> T getByKeyAndLevel(JSONObject root, Integer level, Integer currentLevel, String fieldName) {
        ++currentLevel;
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (fieldName.equalsIgnoreCase(key) && level.equals(currentLevel)) {
                /**
                 * 当key和field的name相同时 -> 表明目标值已经确定
                 */
//                log.info("目标值已经找到:{}", value);
                return (T) value;
            } else {

                if (value instanceof JSONObject) {
                    Object result = getByKeyAndLevel((JSONObject) value, level, currentLevel, fieldName);
                    if (null != result) {//加入非null才返回! 这点很重要!!
                        return (T) result;
                    }
                } else if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    for (Object jsonObject : jsonArray) {
                        Object result = getByKeyAndLevel((JSONObject) jsonObject, level, currentLevel, key);
                        if (null != result) {//加入非null才返回! 这点很重要!!
                            return (T) result;
                        }
                    }
                }
            }
        }
        return null;

    }

    /**
     * @param root
     * @param fieldName 要获取的value的key
     * @param <T>
     * @return 注意:
     * 这里返回前会判断 -> 把递归多个返回值的情况下归为1条路径即可!!!
     */
    public static <T> T getByKey(JSONObject root, String fieldName) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (fieldName.equalsIgnoreCase(key)) {
                /**
                 * 当key和field的name相同时 -> 表明目标值已经确定
                 */
//                log.info("目标值已经找到:{}", value);
                return (T) value;
            } else {

                if (value instanceof JSONObject) {
                    Object result = getByKey((JSONObject) value, fieldName);
                    if (null != result) {//加入非null才返回! 这点很重要!!
                        return (T) result;
                    }
                } else if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    for (Object jsonObject : jsonArray) {
                        Object result = getByKey((JSONObject) jsonObject, key);
                        if (null != result) {//加入非null才返回! 这点很重要!!
                            return (T) result;
                        }
                    }
                }
            }
        }
        return null;

    }
}
