/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.request.index.doc.reindex;

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
public class ReIndexRequest {
    private String conflicts;
    private Source source;
    private Dest dest;
    private Script script;

    @Data
    public static class Source {
        private String index;
        private Integer size;
        private Slice slice;
        private String query;
        private Remote remote;
        private List<String> _source;
    }

    @Data
    public static class Dest {
        private String index;
        private String routing;
        private String version_type;
        private String op_type;
    }

    @Data
    public static class Script {
        private String lang;
        private String source;
    }

    @Data
    public static class Slice {
        private Integer id;
        private Integer max;
    }

    @Data
    public static class Remote {
        private String host;
        private String username;
        private String password;
        private String socket_timeout;
        private String connect_timeout;
    }


}