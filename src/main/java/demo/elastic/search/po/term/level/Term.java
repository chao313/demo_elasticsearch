package demo.elastic.search.po.term.level;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "term": {
 *             "user": {
 *                 "value": "Kimchy",
 *                 "boost": 1.0
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Term {

    @JSONField(name = "field")
    private Field field;

    class Field {
        @JSONField(name = "value")
        String value;
        @JSONField(name = "boost")
        Double boost;
    }
}
