package demo.elastic.search.feign.test;

import demo.elastic.search.feign.ClusterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ClusterServiceTest {

    @Autowired
    private ClusterService clusterService;

    /**
     * alias                        index                           filter routing.index routing.search
     * comstore_tb_object_6684      comstore_tb_object_6684_v2      -      -             -
     * tb_object_6516               tb_object_6516_v5               -      -             -
     * comstore_tb_object_6665      comstore_tb_object_6665_v1      -      -             -
     * tb_object_6186               tb_object_6186_v8               -      -             -
     * comstore_tb_object_6785      comstore_tb_object_6785_v1      -      -             -
     * comstore_tb_object_6913      comstore_tb_object_6913_v1      -      -             -
     */
    @Test
    public void _cat_aliases() {
        String response = clusterService._cluster_allocation_explain(true, true, null, null, true, null);
        log.info("response:{}", response);
    }

}
