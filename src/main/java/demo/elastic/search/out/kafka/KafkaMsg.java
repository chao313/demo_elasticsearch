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

    public final static String TB_OBJECT_6254 = "WIND.TB_OBJECT_6254";
    public final static String TB_OBJECT_0088 = "WIND.TB_OBJECT_0088";
    public final static String TB_OBJECT_6428 = "WIND.TB_OBJECT_6428";

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

    protected JSONObject getJson6254(List<JSONObject> jsonObjects) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSON(jsonObjects).toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", jsonArray);

        /**
         * 关联0088和6428
         */
        result.put(TB_OBJECT_6254, jsonObject);

        return result;
    }

    protected JSONObject getJson0088(List<JSONObject> jsonObjects) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray0088 = JSONArray.parseArray(JSON.toJSON(jsonObjects).toString());
        JSONObject data0088 = new JSONObject();
        data0088.put("data", jsonArray0088);

        result.put(TB_OBJECT_0088, data0088);

        return result;
    }


    protected JSONObject getJson0088(List<JSONObject> jsonObjects, String time) {
        JSONObject result = new JSONObject();

        JSONArray jsonArray0088 = JSONArray.parseArray(JSON.toJSON(jsonObjects).toString());
        JSONObject data0088 = new JSONObject();
        data0088.put("data", jsonArray0088);

        /**
         * 补全6428
         */
        jsonArray0088.forEach(json0088 -> {
            JSONObject json6428 = new JSONObject();
            json6428.put("F2_6428", time);
            JSONArray jsonArray6428 = new JSONArray();
            jsonArray6428.add(json6428);
            JSONObject data6428 = new JSONObject();
            data6428.put("data", jsonArray6428);
            ((JSONObject) json0088).put(TB_OBJECT_6428, data6428);
        });

        /**
         *
         */
        result.put(TB_OBJECT_0088, data0088);

        return result;
    }

}