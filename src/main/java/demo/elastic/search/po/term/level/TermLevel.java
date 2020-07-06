package demo.elastic.search.po.term.level;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.po.term.level.base.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class TermLevel {

    private List<Term> term = new ArrayList<>();
    private List<Exists> exists = new ArrayList<>();
    private List<Regexp> regexp = new ArrayList<>();
    private List<Prefix> prefix = new ArrayList<>();
    private List<Range> range = new ArrayList<>();
    private List<Wildcard> wildcard = new ArrayList<>();
    private List<Terms> terms = new ArrayList<>();
    private List<IDs> ids = new ArrayList<>();
    private List<Fuzzy> fuzzy = new ArrayList<>();

    //private List<Type> type;//已经弃用

    public static JSONArray dealTermLevel(TermLevel termLevel) {
        JSONArray result = new JSONArray();
        if (null == termLevel) {
            /**
             * 为null,直接return
             */
            return result;
        }

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

}