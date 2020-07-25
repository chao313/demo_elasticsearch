package demo.elastic.search.po.request.snapshot;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 * {
 *   "type": "fs",
 *   "settings": {
 *     "location": "my_backup_location"
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSnapshotRepositoryFS {

    @ApiModelProperty(example = "fs")
    private String type = "fs";

    private FSSettings settings;

    /**
     * location
     * 快照的位置。必选
     * compress
     * 打开快照文件的压缩。压缩仅应用于元数据文件（索引映射和设置）。数据文件未压缩。默认为true。
     * chunk_size
     * 如果需要，可以在快照过程中将大文件分解为多个块。指定块尺寸的值，并且单元，例如：1GB，10MB，5KB，500B。默认为null（无限制的块大小）。
     * max_restore_bytes_per_sec
     * 每节点节流恢复率。默认为40mb每秒。
     * max_snapshot_bytes_per_sec
     * 每个节点的快照速率限制。默认为40mb每秒。
     * readonly
     * 使存储库为只读。默认为false。
     */
    @Data
    class FSSettings {
        //快照的位置。必选
        private String location;
        //打开快照文件的压缩。压缩仅应用于元数据文件（索引映射和设置）。数据文件未压缩。默认为true
        @ApiModelProperty(example = "true")
        private Boolean compress = true;
        //如果需要，可以在快照过程中将大文件分解为多个块。指定块尺寸的值，并且单元，例如：1GB，10MB，5KB，500B。默认为null（无限制的块大小）。
        @ApiModelProperty(example = "null")
        private String chunk_size = null;
        //每节点节流恢复率。默认为40mb每秒
        @ApiModelProperty(example = "40mb")
        private String max_restore_bytes_per_sec = "40mb";
        //使存储库为只读。默认为false。
        @ApiModelProperty(example = "false")
        private Boolean readonly = false;
    }


}
