package demo.elastic.search.po.request.snapshot;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * <pre>
 * {
 *   "indices": "index_1,index_2",
 *   "ignore_unavailable": true,
 *   "include_global_state": false,
 *   "metadata": {
 *     "taken_by": "kimchy",
 *     "taken_because": "backup before upgrading"
 *   }
 * }
 * </pre>
 * 默认情况下，快照会备份群集中的所有打开的索引。您可以通过在快照请求的正文中指定索引列表来更改此行为
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSnapshot {
    private String indices;
    private Boolean ignore_unavailable;
    private Boolean include_global_state;
    private Metadata metadata;

    @Data
    public class Metadata {
        private String taken_by;
        private String taken_because;
    }

}
