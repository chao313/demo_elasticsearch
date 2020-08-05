package demo.elastic.search.out.hbase.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class HBaseConfig {

    @Autowired
    HBaseConfigProperties hBaseConfigProperties;

    @Bean(name = "hbaseConn")
    public Connection getHbaseConfiguration() {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        for (Map.Entry<Object, Object> entry : hBaseConfigProperties.getConfig().entrySet()) {
            conf.set(entry.getKey().toString(), entry.getValue().toString());
        }

        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (Exception e) {
            log.error("", e);
        }

        if (connection == null) {
            log.error("无法创建Hbase连接，服务即将退出");
            System.exit(0);
        }

        return connection;
    }
}
