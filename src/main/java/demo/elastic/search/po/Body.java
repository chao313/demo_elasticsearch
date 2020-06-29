/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2020-06-29 10:56:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Body {

    @JSONField(name = "query")
    private Query query;
//    @JSONField(name = "from")
//    private Integer from;
//    @JSONField(name = "size")
//    private Integer size;
//    @JSONField(name = "sort")
//    private List<String> sort;
//    @JSONField(name = "aggs")
//    private Aggs aggs;

}