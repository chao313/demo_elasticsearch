package demo.elastic.search.out.kafka.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class MsgHead {
    @JSONField(name = "PolicyId")
    private String policyId;

    @JSONField(name = "EncType")
    private String encType;

    @JSONField(name = "Topic")
    private String topic;

    @JSONField(name = "UpdateTime")
    private String updateTime;

    @JSONField(name = "DownloadTime")
    private String downloadTime;

    @JSONField(name = "Url")
    private String url;

    @JSONField(name = "Extend")
    private String extend;

    @JSONField(name = "OriId")
    private String oriId;
}
