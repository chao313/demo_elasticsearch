//package demo.elastic.search.out.es.service;
//
//import com.wind.esdatasyn.config.KafkaProperties;
//import com.wind.esdatasyn.config.SchemaConfig;
//import com.wind.esdatasyn.config.SystemConfig;
//import com.wind.esdatasyn.dao.config.FullRfreshTaskDao;
//import com.wind.esdatasyn.model.EsSynSchema;
//import com.wind.esdatasyn.model.FullRefreshTask;
//import com.wind.esdatasyn.model.PartitionPos;
//import com.wind.esdatasyn.util.Constant;
//import kafka.common.ErrorMapping;
//import kafka.common.OffsetMetadataAndError;
//import kafka.common.TopicAndPartition;
//import kafka.javaapi.*;
//import kafka.javaapi.consumer.SimpleConsumer;
//import kafka.network.BlockingChannel;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.kafka.common.TopicPartition;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.util.*;
//
//
//@S
//@Service
//public class FullSynEsService {
//
//
//    @Autowired
//    private EsUpDaterService esUpDaterService;
//
//    @Autowired
//    private FullRfreshTaskDao fullRfreshTaskDao;
//
//    @Autowired
//    private KafkaService kafkaService;
//
//    @Autowired
//    private KafkaProperties kafkaProperties;
//
//    @Autowired
//    private EsService esService;
//
//    @Value("${wind.run.mode}")
//    private String windRunMode;
//
//    public void refreshAll() {
//        // 查询待处理的全量刷表计算任务
//        List<FullRefreshTask> fullCountTaskList = fullRfreshTaskDao.queryFullCountTaskList();
//        if (CollectionUtils.isEmpty(fullCountTaskList)) {
//            return;
//        }
//        // 循环处理每一个全量刷新任务
//        for (FullRefreshTask fullCountTask : fullCountTaskList) {
//            String failReason = null;
//            String topicName = null;
//            List<PartitionPos> partitionPosList = null;
//            boolean hasRefresh = false;
//            try {
//                EsSynSchema diskSchema = SchemaConfig.getSchema(fullCountTask.getIndexName());
//                if (diskSchema == null) {
//                    continue;
//                }
//                String path = "full";
//                File pathFile = new File(path);
//                if (!pathFile.exists()) {
//                    pathFile.mkdirs();
//                }
//                int beginNum = 0;
//                String realFileName = diskSchema.getSourceTable() + "_" + String.valueOf(System.currentTimeMillis());
//                if (StringUtils.equals("1", fullCountTask.getIsBreakpointRefresh())
//                        && StringUtils.isNotBlank(fullCountTask.getFileName())
//                        && StringUtils.isNumeric(fullCountTask.getBeginNum())) {
//                    beginNum = Integer.parseInt(fullCountTask.getBeginNum());
//                    realFileName = fullCountTask.getFileName();
//                }
//                String allFileName = path + File.separator + realFileName;
//                File file = new File(allFileName);
//                if (StringUtils.equals("1", fullCountTask.getIsBreakpointRefresh())) {
//
//                    if (!file.exists()) {
//                        continue;
//                    }
//                }
//                // 多实例竞争当前的全表刷新任务
//                if (!competeTask(fullCountTask)) {
//                    continue;
//                }
//                hasRefresh = true;
//                boolean retFlag = true;
//                if (!StringUtils.equals("1", fullCountTask.getIsBreakpointRefresh())) {
//                    if ("1".equals(fullCountTask.getIsCreatNewIndex())
//                            && (!esService.indexExists(fullCountTask.getRealIndexName()))) {
//                        retFlag = esService.createIndex(diskSchema, fullCountTask.getRealIndexName());
//                    } else if ("1".equals(fullCountTask.getIsCreatNewIndex())
//                            && (esService.indexExists(fullCountTask.getRealIndexName()))) {
//                        LOGGER.error("isCreateNewIndex={} but index={} already exist!", fullCountTask.getIsCreatNewIndex(),
//                                fullCountTask.getRealIndexName());
//                        continue;
//                    }
//                    if ("0".equals(fullCountTask.getIsCreatNewIndex())
//                            && (!esService.indexExists(fullCountTask.getRealIndexName()))) {
//                        LOGGER.error("isCreateNewIndex={} but index={} not exist!", fullCountTask.getIsCreatNewIndex(),
//                                fullCountTask.getRealIndexName());
//                        continue;
//                    }
//                }
//
//                if (!retFlag) {
//                    failReason = "创建索引失败";
//                    continue;
//                }
//                if (!esService.indexExists(fullCountTask.getRealIndexName())) {
//                    continue;
//                }
//
//                if ("hbase".equalsIgnoreCase(diskSchema.getSourceType())) {
//                    esUpDaterService.startRefreshBuilderIndexHbase(diskSchema, fullCountTask.getRealIndexName(), file,
//                            fullCountTask.getIsBreakpointRefresh(), beginNum);
//                } else {// 开始全量刷新ES
//                    esUpDaterService.startRefreshBuilderIndex(diskSchema, fullCountTask.getRealIndexName(), file,
//                            fullCountTask.getIsBreakpointRefresh(), beginNum);
//                }
//            } catch (Exception e) {
//                LOGGER.error("", e);
//                failReason = e.getMessage();
//            } finally {
//                if (hasRefresh) {
//                    competeTask(failReason, fullCountTask.getId());
//                }
//            }
//        }
//    }
//
//    /**
//     * 获取当前已消费的位置
//     *
//     * @param topic
//     * @return
//     */
//    public List<PartitionPos> getPosition(String topic) {
//        String clientId = UUID.randomUUID().toString();
//        String brokers = kafkaProperties.getConsume().get("bootstrap.servers").toString();
//        String group = kafkaProperties.getConsume().getProperty("group.id").toString();
//        List<String> hosts = new ArrayList();
//        List<Integer> ports = new ArrayList();
//        String[] hostsAndPort = brokers.split(",");
//
//        List<PartitionPos> partitionPosList = new ArrayList<>();
//        for (int i = 0; i < hostsAndPort.length; i++) {
//            String host = hostsAndPort[i].split(":")[0];
//            hosts.add(host);
//            ports.add(Integer.parseInt(hostsAndPort[i].split(":")[1]));
//        }
//
//        int correlationId = 0;
//        boolean hasFlag = false;
//        for (int i = 0; i < hosts.size(); i++) {
//            String broker = hosts.get(i);
//            int port = ports.get(i);
//            BlockingChannel channel = new BlockingChannel(broker, port, BlockingChannel.UseDefaultBufferSize(),
//                    BlockingChannel.UseDefaultBufferSize(), 5000);
//            try {
//
//                channel.connect();
//                List<String> seeds = new ArrayList<String>();
//                seeds.add(broker);
//                TreeMap<Integer, PartitionMetadata> metadatas = findLeader(seeds, port, topic);
//                // long sumOffset = 0L;
//                List<TopicAndPartition> partitions = new ArrayList<TopicAndPartition>();
//                for (Map.Entry<Integer, PartitionMetadata> entry : metadatas.entrySet()) {
//                    int partition = entry.getKey();
//                    TopicAndPartition testPartition = new TopicAndPartition(topic, partition);
//                    partitions.add(testPartition);
//                }
//                OffsetFetchRequest fetchRequest = new OffsetFetchRequest(group, partitions, (short) 2, correlationId, clientId);
//                for (Map.Entry<Integer, PartitionMetadata> entry : metadatas.entrySet()) {
//                    int partition = entry.getKey();
//                    try {
//                        channel.send(fetchRequest.underlying());
//                        OffsetFetchResponse fetchResponse = OffsetFetchResponse.readFrom(channel.receive().payload());
//                        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
//                        OffsetMetadataAndError result = fetchResponse.offsets().get(topicAndPartition);
//                        short offsetFetchErrorCode = result.error();
//                        if (offsetFetchErrorCode == ErrorMapping.NotCoordinatorForConsumerCode()) {
//                        } else {
//                            long retrievedOffset = result.offset();
//                            partitionPosList.add(new PartitionPos(new TopicPartition(topic, partition), retrievedOffset));
//                            hasFlag = true;
//                        }
//                    } catch (Exception e) {
//                        LOGGER.error("", e);
//                    }
//                }
//
//                if (hasFlag) {
//                    return partitionPosList;
//                }
//            } catch (Exception e) {
//                LOGGER.error("", e);
//            } finally {
//                if (channel.isConnected()) {
//                    channel.disconnect();
//                }
//            }
//        }
//        return partitionPosList;
//    }
//
//    public TreeMap<Integer, PartitionMetadata> findLeader(List<String> a_seedBrokers, int a_port, String a_topic) {
//        TreeMap<Integer, PartitionMetadata> map = new TreeMap<Integer, PartitionMetadata>();
//        loop: for (String seed : a_seedBrokers) {
//            SimpleConsumer consumer = null;
//            try {
//                consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup" + System.currentTimeMillis());
//                List<String> topics = Collections.singletonList(a_topic);
//                TopicMetadataRequest req = new TopicMetadataRequest(topics);
//                kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
//
//                List<TopicMetadata> metaData = resp.topicsMetadata();
//                for (TopicMetadata item : metaData) {
//                    for (PartitionMetadata part : item.partitionsMetadata()) {
//                        map.put(part.partitionId(), part);
//                    }
//                }
//            } catch (Exception e) {
//                LOGGER.error("Error communicating with Broker [" + seed + "] to find Leader for [" + a_topic + ", ] Reason: ", e);
//            } finally {
//                if (consumer != null)
//                    consumer.close();
//            }
//        }
//        return map;
//    }
//
//    public boolean competeTask(FullRefreshTask task) {
//        int num = fullRfreshTaskDao.updateTaskBegin(task.getId(), SystemConfig.getLocalHost());
//        if (num == 0) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean competeTask(String failReson, long id) {
//        int num = fullRfreshTaskDao.updateTaskDbEnd(failReson, id);
//        if (num == 0) {
//            return false;
//        }
//        return true;
//    }
//
//}
