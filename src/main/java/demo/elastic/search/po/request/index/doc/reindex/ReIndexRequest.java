/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.request.index.doc.reindex;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import demo.elastic.search.config.MyJackSonConverter;
import demo.elastic.search.po.request.dsl.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 复制index请求
 * <pre>
 * {
 *   "conflicts":""
 *   "source": {
 *     "index": "source",
 *     "size": 10,
 * 	 "slice": {
 *       "id": 0,
 *       "max": 2
 *     },
 * 	 "query": {
 *       "term": {
 *         "user": "kimchy"
 *       }
 *     },
 * 	 "remote": {
 *       "host": "http://otherhost:9200",
 *       "username": "user",
 *       "password": "pass",
 * 	  "socket_timeout": "1m",
 *       "connect_timeout": "10s"
 *     },
 * 	"_source": ["user", "_doc"]
 *   },
 *   "dest": {
 *     "index": "dest",
 *     "routing": "=cat",
 * 	 "version_type": "external",
 * 	 "op_type":"",
 *
 *   },
 *   "script": {
 *     "lang": "painless",
 *     "source": "ctx._index = 'metricbeat-' + (ctx._index.substring('metricbeat-'.length(), ctx._index.length())) + '-1'"
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(converter = MyJackSonConverter.class)
public class ReIndexRequest {
    @ApiModelProperty(allowableValues = "proceed,abort", example = "abort")
    //（可选，枚举）设置为proceed即使发生冲突也继续重新编制索引。默认为abort
    private String conflicts;
    private Source source = new Source();
    private Dest dest = new Dest();
    private Script script = new Script();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Source {
        //（必需，字符串）要从中复制索引的名称。还接受以逗号分隔的索引列表，以便从多个来源重新索引
        private String index;
        //（可选，整数）每批要编制索引的文档数。从远程建立索引时使用，以确保批处理适合堆上缓冲区，该缓冲区默认最大大小为100 MB。
        @ApiModelProperty(example = "100")
        private Integer size;
        private Slice slice;
        //（可选，整数）要重新编制索引的最大文档数
        private Integer max_docs;
        private DSLQuery query;
        //（可选，查询对象）指定要使用查询DSL重新编制索引的文档
        private Remote remote = new Remote();
        //（可选，字符串）如果重新true索引所有源字段。设置为列表以重新索引选择字段。默认为true。
        private List<String> _source;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Dest {
        private String index;
        //（必需，字符串）要复制到的索引的名称
        private String version_type;
        //（可选，枚举）设置为仅创建不存在的索引文档（如果不存在，则放置）。有效值：index，create。默认为index
        private String op_type;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Script {
        //（可选，枚举）脚本语言：painless，expression，mustache，java
        private String lang;
        //（可选，字符串）重新编制索引时运行以更新文档源或元数据的脚本
        private String source;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Slice {
        //（可选，整数）用于手动切片的切片ID
        private Integer id;
        //（可选，整数）切片总数
        private Integer max;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Remote {
        //（可选，字符串）要从中索引的Elasticsearch远程实例的URL 。从远程索引时是必需的
        private String host;
        //（可选，字符串）用于与远程主机进行身份验证的用户名
        private String username;
        //可选，字符串）用于与远程主机进行身份验证的密码
        private String password;
        //（可选，时间单位）远程套接字读取超时。默认为30秒
        @ApiModelProperty(example = "30")
        private String socket_timeout;
        //（可选，时间单位）远程连接超时。默认为30秒
        @ApiModelProperty(example = "30")
        private String connect_timeout;
    }


}