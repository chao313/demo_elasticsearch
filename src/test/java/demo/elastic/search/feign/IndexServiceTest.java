package demo.elastic.search.feign;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class IndexServiceTest {

    @Autowired
    private IndexService indexService;

    @Test
    public void _freeze() {
        String response = indexService._freeze("tmp2");
        log.info("response：{}", response);
    }

    @Test
    public void _unfreeze() {
        String response = indexService._unfreeze("tmp2");
        log.info("response：{}", response);
    }
}
