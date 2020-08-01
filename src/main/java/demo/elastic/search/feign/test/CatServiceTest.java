package demo.elastic.search.feign.test;

import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.ExistsRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CatServiceTest {

    @Autowired
    private CatService catService;

    /**
     * /_cat/analyze
     * /_cat/ansj
     * /_cat/allocation
     * /_cat/shards
     * /_cat/shards/{index}
     * /_cat/master
     * /_cat/nodes
     * /_cat/indices
     * /_cat/indices/{index}
     * /_cat/segments
     * /_cat/segments/{index}
     * /_cat/count
     * /_cat/count/{index}
     * /_cat/recovery
     * /_cat/recovery/{index}
     * /_cat/health
     * /_cat/pending_tasks
     * /_cat/aliases
     * /_cat/aliases/{alias}
     * /_cat/thread_pool
     * /_cat/plugins
     * /_cat/fielddata
     * /_cat/fielddata/{fields}
     * /_cat/nodeattrs
     * /_cat/repositories
     * /_cat/snapshots/{repository}
     */
    @Test
    public void _cat() {
        String response = catService._cat();
        log.info("response:{}", response);
    }

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
        String response = catService._cat_aliases(true);
        log.info("response:{}", response);
    }

    /**
     * shards disk.indices disk.used disk.avail disk.total disk.percent host         ip           node
     * 716        1.1tb     1.6tb      1.8tb      3.4tb           47 10.200.6.168 10.200.6.168 dataNode-dwserver18-4
     * 716        1.1tb     1.6tb      1.8tb      3.4tb           46 10.200.6.168 10.200.6.168 dataNode-dwserver18-3
     */
    @Test
    public void _cat_allocation() {
        String response = catService._cat_allocation(true);
        log.info("response:{}", response);
    }

    /**
     * epoch      timestamp count
     * 1596304547 17:55:47  5464792
     */
    @Test
    public void _cat_count() {
        String response = catService._cat_count(true);
        log.info("response:{}", response);
    }

    /**
     * epoch      timestamp count
     * 1596304614 17:56:54  43987
     */
    @Test
    public void _cat_count_index() {
        String response = catService._cat_count(true, "comstore_tb_object_0088");
        log.info("response:{}", response);
    }

    /**
     * id                     host         ip           node                    total F21_0088 source_node.transport_address source_node.uuid F6_0088 F5_0088 F2_6511 F1_6262   title shard.index F7_0088 source_node.name ES_MOD_TIME index_stats.index F1_6228 F2_6772 F26_0088 F23_0088 shard.state
     * PjwdyR4XRkWzZoIVdWbf-w 10.200.6.45  10.200.6.45  dataNode-dwserver19-4   1.3gb       0b                            0b               0b      0b      0b      0b      0b   1.3gb          0b      0b               0b       226kb                0b      0b      0b       0b       0b          0b
     * yrI-H013RyGjP-YJ1U8pgg 10.200.6.45  10.200.6.45  dataNode-dwserver19-2     2gb       0b                            0b               0b      0b      0b      0b      0b     2gb          0b      0b               0b          0b                0b      0b      0b       0b       0b          0b
     */
    @Test
    public void _cat_fielddata() {
        String response = catService._cat_fielddata(true);
        log.info("response:{}", response);
    }

    /**
     * id                     host         ip           node                    total F21_0088 source_node.transport_address source_node.uuid F6_0088 F5_0088 F2_6511 F1_6262   title shard.index F7_0088 source_node.name ES_MOD_TIME index_stats.index F1_6228 F2_6772 F26_0088 F23_0088 shard.state
     * PjwdyR4XRkWzZoIVdWbf-w 10.200.6.45  10.200.6.45  dataNode-dwserver19-4   1.3gb       0b                            0b               0b      0b      0b      0b      0b   1.3gb          0b      0b               0b       226kb                0b      0b      0b       0b       0b          0b
     * yrI-H013RyGjP-YJ1U8pgg 10.200.6.45  10.200.6.45  dataNode-dwserver19-2     2gb       0b                            0b               0b      0b      0b      0b      0b     2gb          0b      0b               0b          0b                0b      0b      0b       0b       0b          0b
     */
    @Test
    public void _cat_fielddata_field() {
        String response = catService._cat_fielddata_field(true, "F2_0088");
        log.info("response:{}", response);
    }

    /**
     * epoch      timestamp cluster      status node.total node.data shards  pri relo init unassign pending_tasks max_task_wait_time active_shards_percent
     * 1596307035 02:37:15  lzxsearchnew green          15        12   8588 4331    0    0        0             0                  -                100.0%
     */
    @Test
    public void _cat_health() {
        String response = catService._cat_health(true);
        log.info("response:{}", response);
    }


}
