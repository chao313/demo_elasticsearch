package demo.elastic.search.out.kafka;

import demo.elastic.search.out.kafka.vo.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * Kafka生产者
 */
@Slf4j
@Service
public class KafkaProducerService {

    public KafkaProducer<String, String> kafkaProducer;

    @Autowired
    private KafkaProperties kafkaProperties;

    @PostConstruct
    public void init() {
        kafkaProducer = new KafkaProducer<String, String>(kafkaProperties.getProduce());//创建生产者
    }

    /**
     * 使用回调需要实现的接口 -> 作为发送时的参数
     */
    private class ProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                log.info("kafka异常:{}", e.toString(), e);
            }
        }
    }

    /**
     * @param topic 需要发送的topic
     * @param msg   发送的msg
     * @return
     */
    protected boolean load(String topic, String msg) {
        boolean flag = false;
        RecordMetadata recordMetadata = null;
        try {
            kafkaProducer.send(new ProducerRecord<String, String>(topic, msg), new ProducerCallback());
            flag = true;
        } catch (TimeoutException e) {
            log.error("send data to kafka has Exception {}", e.toString(), e);
        }
        return flag;
    }

}