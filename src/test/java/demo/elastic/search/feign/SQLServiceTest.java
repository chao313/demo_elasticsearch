package demo.elastic.search.feign;

import demo.elastic.search.feign.SQLService;
import demo.elastic.search.po.request.sql.SqlRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class SQLServiceTest {


    @Autowired
    private SQLService sqlService;

    @Test
    public void _sql() {
        SqlRequest sqlRequest = SqlRequest.builderRequest("SELECT * FROM index4", 1);
        log.info("请求体:{}", sqlRequest.getRequestBody());
        String result = sqlService._sql("txt", sqlRequest);
        log.info("执行结果:{}", result);
    }

    @Test
    public void _sql_translate() {

        SqlRequest sqlRequest = SqlRequest.builderRequest("SELECT * FROM index4");
        String result = sqlService._sql_translate(sqlRequest);
        log.info("执行结果:{}", result);
    }

}
