package demo.elastic.search.po.request.aggs;


import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Aggs;
import lombok.Data;

/**
 * 聚合
 * "max_price" : { "max" : { "field" : "age" } }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VoidAggs implements Aggs {


}