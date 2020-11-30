package demo.elastic.search.out.kafka;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.out.kafka.vo.KafkaMessage;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class KafkaMsg {


    public enum ToTable {
        TB_OBJECT_NAME("TB_OBJECT_NAME"), TB_OBJECT_PEOPLE("TB_OBJECT_PEOPLE");

        private String table;

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }

        ToTable(String table) {
            this.table = table;
        }

        public static ToTable getOutTypeByType(String table) {
            for (ToTable toTable : ToTable.values()) {
                if (toTable.getTable().equals(table)) {
                    return toTable;
                }
            }
            return null;
        }
    }


    public final static String TB_OBJECT_NAME = "TB_OBJECT_NAME";
    public final static String TB_OBJECT_PEOPLE = "TB_OBJECT_PEOPLE";

    /**
     * @return
     */
    protected KafkaMessage getKafakMsg(String msgId,
                                       String policyId,
                                       JSONObject resultJson) {
        String time = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
        KafkaMessage km = (
                new KafkaMessage
                        .Builder(msgId))
                .policyId(policyId)
                .updateTime(time)
                .downloadTime(time)
                .encType("ORI_FILE")
                .setOriId("")
                .setUrl("")
                .setData("ContentData", resultJson)
                .build();
        return km;
    }



    protected JSONObject getJson(List<JSONObject> jsonObjects) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray0088 = JSONArray.parseArray(JSON.toJSON(jsonObjects).toString());
        JSONObject data0088 = new JSONObject();
        data0088.put("data", jsonArray0088);

        result.put(TB_OBJECT_PEOPLE, data0088);

        return result;
    }

    /**
     * 获取指定的table的数据
     *
     * @param jsonObjects
     * @param table
     * @return
     */
    protected JSONObject getJson(List<JSONObject> jsonObjects, String table) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSON(jsonObjects).toString());
        JSONObject data = new JSONObject();
        data.put("data", jsonArray);
        result.put(table, data);

        return result;
    }



}