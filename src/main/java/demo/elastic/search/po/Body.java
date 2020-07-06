/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.compound.base.Bool;
import demo.elastic.search.po.term.level.TermLevel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    @ApiModelProperty(example = "0")
    @JSONField(name = "from")
    private Integer from;

    @ApiModelProperty(example = "10")
    @JSONField(name = "size")
    private Integer size;

//    @JSONField(name = "sort")
//    private List<String> sort;
//    @JSONField(name = "aggs")
//    private Aggs aggs;


    /**
     * 用于解析请求体
     *
     * @return
     */
    public String parse() {

        JSONObject root = JSONObject
                .parseObject(JSONObject.toJSON(this).toString());

        JSONObject boolJSONObject = root.getJSONObject(Body._query).getJSONObject(Query._bool);

        TermLevel must = this.getQuery().getBool().getMust();
        JSONArray mustJsonArray = TermLevel.dealTermLevel(must);
        boolJSONObject.put(Bool._must, mustJsonArray);

        TermLevel must_not = this.getQuery().getBool().getMust_not();
        JSONArray must_notJsonArray = TermLevel.dealTermLevel(must_not);
        boolJSONObject.put(Bool._must_not, must_notJsonArray);

        TermLevel should = this.getQuery().getBool().getShould();
        JSONArray shouldJsonArray = TermLevel.dealTermLevel(should);
        boolJSONObject.put(Bool._should, shouldJsonArray);

        TermLevel filter = this.getQuery().getBool().getFilter();
        JSONArray filterJsonArray = TermLevel.dealTermLevel(filter);
        boolJSONObject.put(Bool._filter, filterJsonArray);

        return root.toString();
    }


    public static String _query = "query";
    public static String _from = "from";
    public static String _size = "size";
    public static String _sort = "sort";
    public static String _aggs = "aggs";


}