package demo.elastic.search.po.request.snapshot;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 * {
 *   "type": "source",
 *   "settings": {
 *     "delegate_type": "fs",
 *     "location": "my_backup_location"
 *   }
 * </pre>
 * <p>
 * 使用源存储库，您可以创建最少的仅源快照，这些磁盘占用的磁盘空间最多减少50％
 * 仅源快照包含存储的字段和索引元数据。它们不包括索引或doc值结构，并且在还原后不可搜索
 * 恢复的唯一来源快照后，必须重新索引 数据到一个新的索引
 * <p>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSnapshotRepositorySource {

    @ApiModelProperty(example = "source")
    private String type = "source";

    private SourceSettings settings;

    @Data
    public class SourceSettings {
        @ApiModelProperty(example = "fs")
        private String delegate_type;
        @ApiModelProperty(example = "file:/home/es/backups/112")
        private String location;
    }


}
