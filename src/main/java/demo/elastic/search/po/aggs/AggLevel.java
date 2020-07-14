//package demo.elastic.search.po.aggs;
//
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import demo.elastic.search.po.aggs.base.AggTerms;
//import lombok.Data;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//public class AggLevel {
//
//    //terms聚合
//    private List<AggTerms> terms = new ArrayList<>();
//
//    public static JSONArray dealAggLevel(AggLevel aggLevel) {
//        JSONArray result = new JSONArray();
//        if (null == aggLevel) {
//            /**
//             * 为null,直接return
//             */
//            return result;
//        }
//
//        aggLevel.getTerms().forEach(vo -> {
//            String terms = vo.parse();
//            if (!StringUtils.isBlank(terms)) {
//                result.add(JSONObject.parseObject(terms));
//            }
//        });
//
//        return result;
//    }
//
//
//}