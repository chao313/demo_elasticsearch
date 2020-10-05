package demo.elastic.search.feign;

import demo.elastic.search.feign.DocumentService;
//import demo.elastic.search.po.request.dsl.term.TermRequest;
import demo.elastic.search.po.request.index.doc.reindex.ReIndexRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
public class DocumentServiceTest {

//    @Autowired
//    private DocumentService documentService;
//
//
//    @Test
//    public void reindex_test() {
//        ReIndexRequest body = new ReIndexRequest();
//        body.setScript(null);
//        body.getSource().set_source(Arrays.asList("F1_6254", "F2_6254", "F3_6254", "F4_6254"));
//        body.getSource().setIndex("tb_object_6254");
//        body.getSource().getRemote().setHost("http://127.0.0.1:80");
//        body.getSource().setQuery(TermRequest.builderQuery("F2_6254", "公司英文名"));
//        body.getDest().setIndex("tb_object_6254_tmp");
//        String response = documentService._reindex(body);
//        log.info("response:{}", response);
//    }

}
