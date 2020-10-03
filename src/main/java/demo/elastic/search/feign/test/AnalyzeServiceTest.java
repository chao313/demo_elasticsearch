package demo.elastic.search.feign.test;


import demo.elastic.search.feign.AnalyzeService;
import demo.elastic.search.po.request.analyze.AnalyzeRequest;
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
    public void _analyze_string() {
        String body = "{\n" +
                "  \"tokenizer\" : \"standard\",\n" +
                "  \"text\" : \"this is a test\"\n" +
                "}";
        String response = analyzeService._analyze(body);
        log.info("response:{}", response);
    }

    @Test
    public void _analyze() {
        AnalyzeRequest analyzeRequest = AnalyzeRequest.builderRequest(AnalyzeRequest.Analyzer.standard, "this is a test", null);
        log.info("请求体:{}", analyzeRequest.getRequestBody());
        String response = analyzeService._analyze(analyzeRequest);
        log.info("response:{}", response);
    }
}
