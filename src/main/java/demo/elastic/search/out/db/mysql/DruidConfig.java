package demo.elastic.search.out.db.mysql;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DruidConfig {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(DruidConfig.class);


    /**
     * 数据源配置对象
     * Primary 表示默认的对象，Autowire可注入，不是默认的得明确名称注入
     *
     * @return
     */
    @Primary
    @Bean
    @ConfigurationProperties("datasource.mysql")
    public DataSourceProperties countDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 数据源对象
     *
     * @return
     */
    @Primary
    @Bean
    public DataSource countDataSource() {
        return countDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "countJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        com.zaxxer.hikari.HikariDataSource dataSource = (com.zaxxer.hikari.HikariDataSource) countDataSource();
        dataSource.setAutoCommit(true);
        return new JdbcTemplate(dataSource);
    }

    @Primary
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }
}
