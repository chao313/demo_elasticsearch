package demo.elastic.search.out.kafka.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

public class KafkaMessage {
    @JSONField(name = "MsgID")
    private String MsgID;
    @JSONField(name = "MsgHead")
    private demo.elastic.search.out.kafka.vo.MsgHead MsgHead;
    @JSONField(name = "Data")
    private String Data;
    @JSONField(name = "DataArray")
    private String DataArray;

    public String getMsgID() {
        return this.MsgID;
    }

    public MsgHead getMsgHead() {
        return this.MsgHead;
    }

    public String getData() {
        return this.Data;
    }

    public String getDataArray() {
        return this.DataArray;
    }

    private KafkaMessage(KafkaMessage.Builder b) {
        this.MsgID = b.MsgID;
        this.MsgHead = b.MsgHead;
        this.Data = JSON.toJSONString(b.Data);
        this.DataArray = JSON.toJSONString(b.DataArray);
    }

    public static class Builder {
        private String MsgID;
        private MsgHead MsgHead;
        private Map<String, Object> Data;
        private JSONArray DataArray;

        public Builder(String MsgID) {
            this.MsgID = MsgID;
            this.MsgHead = new MsgHead();
            this.Data = new HashMap();
            this.DataArray = new JSONArray();
        }

        public KafkaMessage.Builder msgId(String msgId) {
            this.MsgID = msgId;
            return this;
        }

        public KafkaMessage.Builder policyId(String policyId) {
            this.MsgHead.setPolicyId(policyId);
            return this;
        }

        public KafkaMessage.Builder encType(String encType) {
            this.MsgHead.setEncType(encType);
            return this;
        }

        public KafkaMessage.Builder topic(String topic) {
            this.MsgHead.setTopic(topic);
            return this;
        }

        public KafkaMessage.Builder updateTime(String UpdateTime) {
            this.MsgHead.setUpdateTime(UpdateTime);
            return this;
        }

        public KafkaMessage.Builder downloadTime(String DownloadTime) {
            this.MsgHead.setDownloadTime(DownloadTime);
            return this;
        }

        public KafkaMessage.Builder setUrl(String url) {
            this.MsgHead.setUrl(url);
            return this;
        }

        public KafkaMessage.Builder setExtend(String extend) {
            this.MsgHead.setExtend(extend);
            return this;
        }

        public KafkaMessage.Builder setOriId(String oriId) {
            this.MsgHead.setOriId(oriId);
            return this;
        }

        public KafkaMessage.Builder setData(String key, Object obj) {
            this.Data.put(key, obj);
            return this;
        }

        public KafkaMessage.Builder setDataArray(JSONArray dataArray) {
            this.DataArray = dataArray;
            return this;
        }

        public KafkaMessage build() {
            return new KafkaMessage(this);
        }
    }
}
