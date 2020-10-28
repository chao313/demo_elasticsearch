package demo.elastic.search.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class StringToJson {

    /**
     * 按行转换成JSONObject
     *
     * @param source
     * @return
     * @throws IOException
     */
    public static JsonNode toJSON(String source) throws IOException {
        List<String> lines = IOUtils.readLines(new ByteArrayInputStream(source.getBytes()));
        if (lines.size() == 0) {
            return null;
        }
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        if (lines.size() == 1) {
            //当只有一行
            String[] titles = lines.get(0).split("[ ]*");
            for (int i = 0; i < titles.length; i++) {
                jsonNode.put(titles[i], "");
            }
            return jsonNode;
        }

        if (lines.size() == 2) {
            //当只有两行
            String[] titles = lines.get(0).split("[ ]+");
            String[] values = lines.get(1).split("[ ]+");
            for (int i = 0; i < titles.length; i++) {
                jsonNode.put(titles[i], values[i]);
            }
        }
        JsonNode result = getSortJson(jsonNode);
        return result;
    }

    /**
     * 按行转换成JSONObject
     *
     * @param source
     * @return
     * @throws IOException
     */
    public static ArrayNode toJSONArray(String source) throws IOException {
        List<String> lines = IOUtils.readLines(new ByteArrayInputStream(source.getBytes()));
        if (lines.size() == 0) {
            return null;
        }
        ArrayNode jsonNodes = JsonNodeFactory.instance.arrayNode();
        if (lines.size() == 1) {
            ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
            //当只有一行
            String[] titles = lines.get(0).split("[ ]*");
            for (int i = 0; i < titles.length; i++) {
                jsonNode.put(titles[i], "");
            }
            JsonNode sortJson = getSortJson(jsonNode);
            jsonNodes.add(sortJson);
            return jsonNodes;
        }

        if (lines.size() >= 2) {
            String title = lines.get(0).replaceAll("[ ]([a-zA-Z])", "\\$$1");
            //当大于1行
            String[] titles = title.split("\\$");

            for (int i = 1; i < lines.size(); i++) {
                ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
                String line = lines.get(i);
                for (int j = 0; j < titles.length; j++) {
                    String value = line.substring(0, titles[j].length());
                    if (line.length() > titles[j].length()) {
                        line = line.substring(titles[j].length() + 1);//截取一行
                    } else {
                        //最后一行就是本身长度
                        line = line.substring(titles[j].length());//截取一行
                    }
                    jsonNode.put(titles[j].trim(), value.trim());
                }
                JsonNode sortJson = getSortJson(jsonNode);
                jsonNodes.add(sortJson);
            }
        }
        return jsonNodes;
    }

    public static JsonNode getSortJson(JsonNode json) throws JsonProcessingException {
        JSONObject jsonObjectTmp = JSONObject.parseObject(json.toString());
        Set<String> set = jsonObjectTmp.keySet();
        SortedMap map = new TreeMap();
        set.forEach(key -> {
            Object value = json.get(key);
            map.put(key, value);
        });
        String s = new JsonMapper().writeValueAsString(map);
        JsonNode jsonNode = new JsonMapper().readTree(s);
        return jsonNode;
    }

    public static JsonNode getSortJson(String source) throws JsonProcessingException {
        JSONObject jsonObjectTmp = JSONObject.parseObject(source);
        Set<String> set = jsonObjectTmp.keySet();
        //排序 -> key去除下划线排序
        TreeMap map = new TreeMap();
        set.forEach(key -> {
            Object value = jsonObjectTmp.get(key);
            map.put(key, value);
        });
        String s = new JsonMapper().writeValueAsString(map);
        JsonNode jsonNode = new JsonMapper().readTree(s);
        return jsonNode;
    }


    public static JsonNode getSortJson(JSONObject jsonObject) throws JsonProcessingException {
        Set<String> set = jsonObject.keySet();
        //排序 -> key去除下划线排序
        TreeMap map = new TreeMap(new FieldComparator());
        set.forEach(key -> {
            Object value = jsonObject.get(key);
            map.put(key, value);
        });
        String s = new JsonMapper().writeValueAsString(map);
        JsonNode jsonNode = new JsonMapper().readTree(s);
        return jsonNode;
    }

}
