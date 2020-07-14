/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.po.aggs.base.AggTerms;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class Aggs {

    //terms聚合
    private List<AggTerms> terms = new ArrayList<>();

    public static JSONObject dealAggs(Aggs aggs) {
        JSONObject result = new JSONObject();
        if (null == aggs) {
            /**
             * 为null,直接return
             */
            return result;
        }

        aggs.getTerms().forEach(vo -> {
            String terms = vo.parse();
            if (!StringUtils.isBlank(terms)) {
                result.put(vo.getAggregation_name(), JSONObject.parseObject(terms));

            }
        });

        return result;
    }


    public static String _aggs = "aggs";
}