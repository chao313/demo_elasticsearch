/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  curl -X GET  "http://39.107.236.187:9200/bank/_doc/_search?pretty" -H 'Content-Type: application/json' -d'
 *  {
 *     "from": 0,
 *     "size": 1,
 *     "query": {
 *         "bool": {
 *             "must":[
 *              {"exists": {"field": "age"}},
 *              {"exists": {"field": "address"}},
 *              {"term": {"age": {
 *                             "boost": 0,
 *                             "value": 32
 *                         }}
 *              }
 *             ]
 *         }
 *     }
 * }'
 * </pre>
 */
@Data
public class Body {

    @JSONField(name = "query")
    private Query query;
    @JSONField(name = "from")
    private Integer from;
    @JSONField(name = "size")
    private Integer size;
    @JSONField(name = "sort")
    private List<String> sort;
    @JSONField(name = "aggs")
    private Aggs aggs;


    public static String _query = "query";
    public static String _from = "from";
    public static String _size = "size";
    public static String _sort = "sort";
    public static String _aggs = "aggs";

    /**
     * 用于解析请求体
     *
     * @return
     */
    public String parse() {


        JSONObject root = JSONObject
                .parseObject(JSONObject.toJSON(this).toString());

        root.getJSONArray(_query)

        return root.toString();
    }
}