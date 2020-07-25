package demo.elastic.search.po.request.snapshot;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 * {
 *   "type": "url",
 *   "settings": {
 *     "url": "file:/mount/backups/my_fs_backup_location"
 *   }
 * }
 * </pre>
 * <p>
 * 为了降低这种风险，您可以使用URL存储库（"type": "url"）为一个或多个集群提供对共享文件系统存储库的只读访问权限
 * <p>
 * 使用该file协议的URL 必须指向群集中所有主节点和数据节点均可访问的共享文件系统的位置。该位置必须在path.repo设置中注册，类似于 共享文件系统存储库
 * 使用的URL ftp，http，https，或jar不需要的协议在登记path.repo设定
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSnapshotRepositoryURL {

    @ApiModelProperty(example = "url")
    private String type = "url";

    private URLSettings settings;


    @Data
    class URLSettings {
        //url
        @ApiModelProperty(example = "file:/home/es/backups/112")
        private String url;
    }


}
