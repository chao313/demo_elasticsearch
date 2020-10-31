# 用于学习 ElasticSearch 的 demo

[swagger](http://127.0.0.1:9000/demo_elasticsearch/swagger-ui.html)

目标功能
1. cluster
   * 查看集群的自己的信息（概览）
     * 集群版本
     * 集群状态
     * 集群证书
       * 证书状态
       * 证书删除
       * 证书更新
       * 证书使用
     * 集群铂金信息
     * 集群全部文档数量
     * 集群远程信息
     * 集群设置 + 跳转
     * 集群node数量 + 跳转
     * 集群index数量 + 跳转
     * 集群shard数量 + 跳转
     * 集群segment数量 + 跳转
     * 集群插件数量 + 跳转
     * 集群仓库数量 + 跳转
     * 集群快照数量 + 跳转
     * 集群模板数量 + 跳转
   * 集群index列表（index新建）
     * index删除
     * 跳转查看index信息
     * 跳转查看index别名列表
     * 跳转查看index映射列表
     * 跳转查看index文档列表
     * 跳转查看index分片列表
     * 跳转查看index段列表
     * 跳转查看index恢复列表
     * 跳转设置index
   * 集群alias列表(同一个别名可能对应多个Index，ES返回的是{index1:...,index2:...},兼容这种情况)

   * 集群node列表
     * node基础信息
   * 集群shard列表
     * shard基础信息
   * 集群segment列表
     * segment基础信息
   * 集群仓库列表
     * 查看快照仓库
     * 注册快照仓库(FS/Source/Url)
     * 删除仓库
   * 集群快照列表
     * 创建快照
     * 还原快照
     * 删除快照(指定仓库指定快照)
     * 检索快照
   * 集群任务列表
     * 任务基础信息
   * 集群模板列表
     * 模板基础信息
2. node
   * 节点的各种指标(JVM,版本,设置,IP,端口,插件...)
3. index
   * index信息
   * index别名列表（别名新增）
   * index映射列表（映射新增）
      * 映射删除
      * 映射更新
   * index文档列表（文档新增）
     * 文档新增(支持批量)
     * 文档删除
     * 文档更新
     * 文档查看
   * index分片列表
     * shard基础信息
   * index段列表
     * shard基础信息
   * index恢复列表
     * 恢复基础信息
   * index设置
     * index刷新
     * index冲洗
     * index冻结/解冻
     * index关闭/打开
     * index分片副本增删改查
     * index映射增删改查
     * index别名增删改查
     * index重新reindex
       * 克隆
       * 复制
       * 削减
       * 拆分
4. Search
   * Search分词检索
   * Search数量检索
   * SearchDSL组合检索
   * Search全文检索
   * SearchDSL术语检索
   * Search解释验证
   * SearchLucene检索
   * Search脚本检索
   * Search滚动检索
   * Search分片检索
   * SearchSQL检索
     * 支持 = <>
     * 支持 IS NULL / IS NOT NULL
     * 支持 REGEXP / NOT REGEXP
     * 支持 LIKE / NOT LIKE
     * 支持 > >= <= < BETWEEN AND
     * 支持 IN / NOT IN
5. Aggregation



* 其他
  * 数据提取进度
  * 日志推送到前端
  * 数据提取管理(终止...)
  * 数据批量操作（in 一个文本）
  * SQL单独放置
  * 修改total的bug + 调查占用大量内存是原因
  * 导出为excel弹出框，去掉确定按钮
  * 解决小图标无法展示问题
  * 导出excel和db时，加入筛选字段
  * 添加导出为CSV,TXT,JSON功能
  * 可以添加导入功能，只支持JSON
  * 资源管理添加文件size
  * 优化vue的sql编辑器的卡顿问题