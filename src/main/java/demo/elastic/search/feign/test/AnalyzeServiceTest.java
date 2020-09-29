package demo.elastic.search.feign.test;


import demo.elastic.search.feign.AnalyzeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AnalyzeServiceTest {


    @Autowired
    private AnalyzeService analyzeService;

    /**
     *
     */
    @Test
    public void _cat_allocation() {
        String body = "{\n" +
                "  \"analyzer\" : \"standard\",\n" +
                "  \"text\" : \"this is a test\"\n" +
                "}";
        String response = analyzeService._analyze(body);
        log.info("response:{}", response);
    }
}
