package demo.elastic.search.po.request.snapshot;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * <pre>
 * {
 *   "indices": "index_1,index_2",
 *   "ignore_unavailable": true,
 *   "include_global_state": false,
 *   "rename_pattern": "index_(.+)",
 *   "rename_replacement": "restored_index_$1",
 *   "include_aliases": false
 * }
 *
 * </pre>
 * 默认情况下，将还原快照中的所有索引，但不还原群集状态
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestoreSnapshot {
    //使用indices参数仅还原特定索引
    private String indices;
    private Boolean ignore_unavailable;
    //还原全局状态
    private Boolean include_global_state;
    //rename_pattern 和rename_replacement 用于在使用恢复的正则表达式支持引用原文重命名索引
    private String rename_pattern;
    private String rename_replacement;
    //还原别名
    private Boolean include_aliases;
}
