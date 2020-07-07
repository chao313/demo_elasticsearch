package demo.elastic.search.out.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.out.kafka.vo.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

/**
 * kafka对外开放的接口
 */
@Service
public class KafkaOutService {

    @Autowired
    private KafkaMsg kafkaMsg;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * 这里只处理0088的数据 -> 加载0088的数据
     *
     * @param topic
     * @param root0088
     * @param policyId
     */
    public void load0088(String topic, JSONObject root0088, String policyId) {

        JSONObject result = kafkaMsg.getJson0088(Arrays.asList(root0088));

        KafkaMessage kafkaMessage = kafkaMsg.getKafakMsg(UUID.randomUUID().toString(),
                policyId, result);
        kafkaProducerService.load(topic, JSON.toJSONString(kafkaMessage));
    }

    /**
     * 这里处理单个的表
     *
     * @param topic
     * @param json
     * @param policyId
     */
    public void load(String topic, JSONObject json, String policyId, String table) {

        JSONObject result = kafkaMsg.getJson(Arrays.asList(json), table);

        KafkaMessage kafkaMessage = kafkaMsg.getKafakMsg(UUID.randomUUID().toString(),
                policyId, result);
        kafkaProducerService.load(topic, JSON.toJSONString(kafkaMessage));
    }
}
