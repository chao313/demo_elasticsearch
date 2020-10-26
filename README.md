# 用于学习 ElasticSearch 的 demo

[swagger](http://127.0.0.1:9000/demo_elasticsearch/swagger-ui.html)

目标功能
1. cluster
   * 查看集群的自己的信息（概览）
     * 集群版本
     * 集群状态
     * 集群证书
     * 集群node数量
     * 集群index数量
     * 集群shard数量
     * 集群segment数量
     * 集群仓库数量
     * 集群快照数量
     * 集群铂金信息
   * 集群index列表
     * 查看index信息
     * 查看index别名
     * 查看index映射
     * 查看index文档
     * 查看index分片
     * 查看index段
     * 查看index恢复
     * 设置index
       * index刷新
       * index冲洗
       * index冻结
       * index修改分片
       * index修改映射
       * index修改别名
       * index重新reindex
   * 集群alias列表
   * 集群node列表
   * 集群shard列表
   * 集群segment列表
   * 集群仓库列表
   * 集群快照列表
   * 集群任务列表
   * 集群模板列表
2. node
   * 查看所有的brokers(list)
   * 查看每个broker的Topics整体信息(topic数量)
   * 查看每个broker的consumer整体信息(consumer数量)
3. index