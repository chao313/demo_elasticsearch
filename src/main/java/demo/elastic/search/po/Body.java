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
        JSONArray mustJsonArray = this.dealTermLevel(must);
        boolJSONObject.put(Bool._must, mustJsonArray);

        return root.toString();
    }

    private JSONArray dealTermLevel(TermLevel termLevel) {
        JSONArray result = new JSONArray();
        termLevel.getExists().forEach(vo -> {
            String exists = vo.parse();
            if (!StringUtils.isBlank(exists)) {
                result.add(JSONObject.parseObject(exists));
            }
        });
        termLevel.getTerm().forEach(vo -> {
            String term = vo.parse();
            if (!StringUtils.isBlank(term)) {
                result.add(JSONObject.parseObject(term));
            }

        });
        termLevel.getRegexp().forEach(vo -> {
            String regexp = vo.parse();
            if (!StringUtils.isBlank(regexp)) {
                result.add(JSONObject.parseObject(regexp));
            }
        });

        termLevel.getPrefix().forEach(vo -> {
            String prefix = vo.parse();
            if (!StringUtils.isBlank(prefix)) {
                result.add(JSONObject.parseObject(prefix));
            }
        });

        termLevel.getRange().forEach(vo -> {
            String range = vo.parse();
            if (!StringUtils.isBlank(range)) {
                result.add(JSONObject.parseObject(range));
            }
        });

        termLevel.getWildcard().forEach(vo -> {
            String wildcard = vo.parse();
            if (!StringUtils.isBlank(wildcard)) {
                result.add(JSONObject.parseObject(wildcard));
            }
        });

        termLevel.getTerms().forEach(vo -> {
            String terms = vo.parse();
            if (!StringUtils.isBlank(terms)) {
                result.add(JSONObject.parseObject(terms));
            }
        });

        termLevel.getIds().forEach(vo -> {
            String ids = vo.parse();
            if (!StringUtils.isBlank(ids)) {
                result.add(JSONObject.parseObject(ids));
            }
        });

        termLevel.getFuzzy().forEach(vo -> {
            String fuzzy = vo.parse();
            if (!StringUtils.isBlank(fuzzy)) {
                result.add(JSONObject.parseObject(fuzzy));
            }
        });


//        termLevel.getType().forEach(vo -> {
//            String type = vo.parse();
//            if (!StringUtils.isBlank(type)) {
//                result.add(JSONObject.parseObject(type));
//            }
//        });


        return result;
    }

    public static String _query = "query";
    public static String _from = "from";
    public static String _size = "size";
    public static String _sort = "sort";
    public static String _aggs = "aggs";


}