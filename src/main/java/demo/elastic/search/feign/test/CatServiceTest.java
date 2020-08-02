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

    /**
     * health status index                           pri rep docs.count docs.deleted store.size pri.store.size
     * green  open   comstore_tb_object_6891_v2        6   1     648635          547    438.5mb        219.2mb
     * green  open   comstore_tb_object_6570_v2        6   1      48916           25     38.5mb         19.2mb
     * green  open   origin_raw_k1org_attach_v1        6   1          0            0      1.8kb           954b
     */
    @Test
    public void _cat_indices() {
        String response = catService._cat_indices(true);
        log.info("response:{}", response);
    }

    /**
     * health status index             pri rep docs.count docs.deleted store.size pri.store.size
     * green  open   tb_object_0088_v2   6   1  183858417     16592942    274.3gb        137.1gb
     */
    @Test
    public void _cat_indices_index() {
        String response = catService._cat_indices_index(true, "tb_object_0088_v2");
        log.info("response:{}", response);
    }

    /**
     * id                     host         ip           node
     * npG4NJqvSc-p5mCimCL3Rg 10.200.2.204 10.200.2.204 masterNode-dwserver17-1
     */
    @Test
    public void _cat_master() {
        String response = catService._cat_master(true);
        log.info("response:{}", response);
    }

    /**
     * node                    host         ip           attr   value
     * dataNode-dwserver17-4   10.200.2.204 10.200.2.204 master false
     * dataNode-dwserver17-3   10.200.2.204 10.200.2.204 master false
     * dataNode-dwserver19-3   10.200.6.45  10.200.6.45  master false
     */
    @Test
    public void _cat_nodeattrs() {
        String response = catService._cat_nodeattrs(true);
        log.info("response:{}", response);
    }

    /**
     * host         ip           heap.percent ram.percent  load node.role master name
     * 10.200.2.204 10.200.2.204           77          97 14.25 d         -      dataNode-dwserver17-4
     * 10.200.2.204 10.200.2.204           85          97 14.71 d         -      dataNode-dwserver17-3
     * 10.200.6.45  10.200.6.45            68          99 15.83 d         -      dataNode-dwserver19-3
     */
    @Test
    public void _cat_nodes() {
        String response = catService._cat_nodes(true);
        log.info("response:{}", response);
    }

    /**
     * insertOrder timeInQueue priority source
     * 1685        855ms       HIGH     update-mapping [foo][t]
     */
    @Test
    public void _cat_pending_tasks() {
        String response = catService._cat_pending_tasks(true);
        log.info("response:{}", response);
    }

    /**
     * name                    component                   version type url
     * dataNode-dwserver17-4   elasticsearch-analysis-ansj 2.3.1   j/s  /_plugin/elasticsearch-analysis-ansj/
     * dataNode-dwserver17-4   head                        master  s    /_plugin/head/
     * dataNode-dwserver17-4   kopf                        2.0.1   s    /_plugin/kopf/
     * dataNode-dwserver17-4   license                     2.3.1   j
     * dataNode-dwserver17-4   marvel-agent                2.3.1   j
     * dataNode-dwserver17-4   sql                         2.3.1.1 j/s  /_plugin/sql/
     */
    @Test
    public void _cat_plugins() {
        String response = catService._cat_plugins(true);
        log.info("response:{}", response);
    }

    /**
     */
    @Test
    public void _cat_recovery() {
        String response = catService._cat_recovery(true);
        log.info("response:{}", response);
    }

    /**
     * index             shard time type    stage source_host  target_host  repository snapshot files files_percent bytes bytes_percent total_files total_bytes translog translog_percent total_translog
     * tb_object_0088_v2 0     1230 replica done  10.200.6.168 10.200.6.45  n/a        n/a      0     0.0%          0     0.0%          0           0           0        100.0%           0
     * tb_object_0088_v2 0     1326 store   done  10.200.6.168 10.200.6.168 n/a        n/a      0     100.0%        0     100.0%        128         21798992400 0        100.0%           0
     * tb_object_0088_v2 1     1104 store   done  10.200.2.204 10.200.2.204 n/a        n/a      0     100.0%        0     100.0%        163         21714846324 0        100.0%           0
     */
    @Test
    public void _cat_recovery_index() {
        String response = catService._cat_recovery_index(true, "tb_object_0088_v2");
        log.info("response:{}", response);
    }

    /**
     * id                          type
     * my_backup                     fs
     * my_read_only_url_repository  url
     */
    @Test
    public void _cat_repositories() {
        String response = catService._cat_repositories(true);
        log.info("response:{}", response);
    }

    /**
     * index                   shard prirep state         docs   store ip         node
     * bank_cloned             0     p      STARTED        948 442.8kb 172.17.0.1 localhost.localdomain
     * bank_cloned             0     r      UNASSIGNED
     * comstore_tb_object_0088 0     p      STARTED      43987  23.3mb 172.17.0.1 localhost.localdomain
     * comstore_tb_object_0088 0     r      UNASSIGNED
     */
    @Test
    public void _cat_shards() {
        String response = catService._cat_shards(true);
        log.info("response:{}", response);
    }

    /**
     * index             shard prirep state       docs  store ip           node
     * tb_object_0088_v2 2     r      STARTED 30649817 22.8gb 10.200.6.168 dataNode-dwserver18-1
     * tb_object_0088_v2 2     p      STARTED 30649817 22.6gb 10.200.2.204 dataNode-dwserver17-2
     * tb_object_0088_v2 3     r      STARTED 30653417 22.6gb 10.200.6.45  dataNode-dwserver19-2
     * tb_object_0088_v2 3     p      STARTED 30653391 22.6gb 10.200.2.204 dataNode-dwserver17-3
     * tb_object_0088_v2 1     p      STARTED 30640375 22.8gb 10.200.2.204 dataNode-dwserver17-1
     * tb_object_0088_v2 1     r      STARTED 30640375 22.8gb 10.200.6.45  dataNode-dwserver19-1
     */
    @Test
    public void _cat_shards_index() {
        String response = catService._cat_shards_index(true, "tb_object_0088_v2");
        log.info("response:{}", response);
    }


    /**
     * index                   shard prirep ip         segment generation docs.count docs.deleted    size size.memory committed searchable version compound
     * comstore_tb_object_0088 0     p      172.17.0.1 _v              31      11173            0   6.7mb        8674 true      true       8.4.0   false
     * comstore_tb_object_0088 0     p      172.17.0.1 _15             41        427            0 312.3kb        5935 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _16             42      11400            0   6.6mb        8796 true      true       8.4.0   false
     * comstore_tb_object_0088 0     p      172.17.0.1 _17             43        383            0 281.2kb        6146 true      true       8.4.0   true
     */
    @Test
    public void _cat_segments() {
        String response = catService._cat_segments(true);
        log.info("response:{}", response);
    }

    /**
     * index                   shard prirep ip         segment generation docs.count docs.deleted    size size.memory committed searchable version compound
     * comstore_tb_object_0088 0     p      172.17.0.1 _v              31      11173            0   6.7mb        8674 true      true       8.4.0   false
     * comstore_tb_object_0088 0     p      172.17.0.1 _15             41        427            0 312.3kb        5935 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _16             42      11400            0   6.6mb        8796 true      true       8.4.0   false
     * comstore_tb_object_0088 0     p      172.17.0.1 _17             43        383            0 281.2kb        6146 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _18             44        617            0   416kb        5605 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _19             45        935            0 614.6kb        5783 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _1a             46         65            0  52.5kb        5573 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _1b             47        768            0 522.3kb        6196 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _1c             48        232            0 162.8kb        6117 true      true       8.4.0   true
     * comstore_tb_object_0088 0     p      172.17.0.1 _1d             49      17987            0   7.6mb       16623 true      true       8.4.0   true
     */
    @Test
    public void _cat_segments_index() {
        String response = catService._cat_segments_index(true, "comstore_tb_object_0088");
        log.info("response:{}", response);
    }

    /**
     * id          status start_epoch start_time end_epoch  end_time duration indices successful_shards failed_shards total_shards
     * snapshot_1 SUCCESS 1595651450  04:30:50   1595651511 04:31:51       1m      19                21             0           21
     * {now}      SUCCESS 1595656343  05:52:23   1595656351 05:52:31     7.6s      19                21             0           21
     */
    @Test
    public void _cat_snapshots() {
        String response = catService._cat_snapshots(true, "112");
        log.info("response:{}", response);
    }


    /**
     * action                         task_id                        parent_task_id                 type      start_time    timestamp running_time ip         node                  description
     * cluster:monitor/tasks/lists    Jj2odOX3TjSYswTdNivY_g:1147903 -                              transport 1596359047818 09:04:07  405.4micros  172.17.0.1 localhost.localdomain
     * cluster:monitor/tasks/lists[n] Jj2odOX3TjSYswTdNivY_g:1147904 Jj2odOX3TjSYswTdNivY_g:1147903 direct    1596359047818 09:04:07  137.7micros  172.17.0.1 localhost.localdomain
     */
    @Test
    public void _cat_tasks() {
        String response = catService._cat_tasks(true, true);
        log.info("response:{}", response);
    }

    /**
     * name                            index_patterns               order      version
     * .monitoring-logstash            [.monitoring-logstash-7-*]   0          7000199
     * .ml-config                      [.ml-config]                 0          7060299
     * .monitoring-alerts-7            [.monitoring-alerts-7]       0          7000199
     * .monitoring-kibana              [.monitoring-kibana-7-*]     0          7000199
     * .transform-internal-004         [.transform-internal-004]    0          7060299
     * .ml-meta                        [.ml-meta]                   0          7060299
     * .monitoring-beats               [.monitoring-beats-7-*]      0          7000199
     */
    @Test
    public void _cat_templates() {
        String response = catService._cat_templates(true);
        log.info("response:{}", response);
    }

    /**
     * name                            index_patterns               order      version
     * .monitoring-logstash            [.monitoring-logstash-7-*]   0          7000199
     */
    @Test
    public void _cat_templates_name() {
        String response = catService._cat_templates(true, ".monitoring-logstash");
        log.info("response:{}", response);
    }

    /**
     * node_name             name                active queue rejected
     * localhost.localdomain analyze                  0     0        0
     * localhost.localdomain ccr                      0     0        0
     * localhost.localdomain fetch_shard_started      0     0        0
     */
    @Test
    public void _cat_thread_pool() {
        String response = catService._cat_thread_pool(true);
        log.info("response:{}", response);
    }

    /**
     * node_name             name                active queue rejected
     * localhost.localdomain ccr                      0     0        0
     */
    @Test
    public void _cat_thread_pool_name() {
        String response = catService._cat_thread_pool(true, "ccr");
        log.info("response:{}", response);
    }

    /**
     * 失败!
     */
    @Test
    public void _cat_transforms() {
        String response = catService._cat_transforms(true);
        log.info("response:{}", response);
    }

}
