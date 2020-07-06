package demo.elastic.search.out.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 配置kafka生产者和消费者
 */
@Component
@ConfigurationProperties(
        prefix = "kafka"
)
public class KafkaProperties {
    private Properties consume;
    private Properties produce;

    public KafkaProperties() {
    }

    public Properties getProduce() {
        return this.produce;
    }

    public void setProduce(Properties produce) {
        this.produce = produce;
    }

    public Properties getConsume() {
        return this.consume;
    }

    public void setConsume(Properties consume) {
        this.consume = consume;
    }

    public String toString() {
        return "KafkaProperties [consume=" + this.consume + ", produce=" + this.produce + "]";
    }
}
