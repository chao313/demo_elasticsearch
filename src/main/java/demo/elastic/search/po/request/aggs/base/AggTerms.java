package demo.elastic.search.po.request.aggs.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.out.remove.compound.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * terms聚合
 * <pre>
 *     "genres" : {
 *             "terms" : {
 *                 "field" : "F26_0088",
 *                 "size" : 5,
 *                 "show_term_doc_count_error": true,
 *                 "order" : { "_count" : "asc" },
 *                 "min_doc_count": 1
 *                }
 *         }
 * </pre>
 */
@Slf4j
@Data
public class AggTerms implements Parse {


    @ApiModelProperty(example = " ")
    @JSONField(name = "aggregation_name")
    String aggregation_name;

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = "10")
    @JSONField(name = "size")
    Integer size;

    @ApiModelProperty(example = "false")
    @JSONField(name = "show_term_doc_count_error")
    Boolean show_term_doc_count_error;

    @ApiModelProperty(example = "1")
    @JSONField(name = "min_doc_count")
    Integer min_doc_count;

    //    @ApiModelProperty(example = "{ \"_count\" : \"asc\" }")
//    @JSONField(name = "order")
//    Map<String, String> order;

    @Override
    public String parse() {
        if (StringUtils.isBlank(aggregation_name)) {
            /**
             * 关键字段为空->返回空字符串
             */
            log.info("指定agg的name为空,不解析");
            return "";
        }
        if (StringUtils.isBlank(field)) {
            /**
             * 关键字段为空->返回空字符串
             */
            log.info("指定字段为空,不解析");
            return "";
        }
        JSONObject terms = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(_field, this.getField());
        if (null != this.getSize()) {
            content.put(_size, this.getSize());
        }
        if (null != this.getShow_term_doc_count_error()) {
            content.put(_show_term_doc_count_error, this.getShow_term_doc_count_error());
        }
//        if (null != this.getOrder()) {
//            content.put(_order, this.getOrder());
//        }
        if (null != this.getMin_doc_count()) {
            content.put(_min_doc_count, this.getMin_doc_count());
        }
        terms.put(_terms, content);
        return terms.toJSONString();
    }


    public static String _terms = "terms";

    public static String _field = "field";
    public static String _size = "size";
    public static String _show_term_doc_count_error = "show_term_doc_count_error";
    public static String _order = "order";
    public static String _min_doc_count = "min_doc_count";
}
