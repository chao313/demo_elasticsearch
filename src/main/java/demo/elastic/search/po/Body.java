/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;

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

    private Query query;
    private Integer from;
    private Integer size;
    private List<String> sort;
    private Aggs aggs;

}