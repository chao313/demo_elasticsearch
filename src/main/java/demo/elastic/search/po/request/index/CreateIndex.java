package demo.elastic.search.po.request.index;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * <pre>
 * {
 *     "settings" : {
 *         "number_of_shards" : 1
 *     },
 *     "mappings" : {
 *         "properties" : {
 *             "field1" : { "type" : "text" }
 *         }
 *     },
 *    "aliases" : {
 *         "alias_1" : {},
 *         "alias_2" : {
 *             "filter" : {
 *                 "term" : {"user" : "kimchy" }
 *             },
 *             "routing" : "kimchy"
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class CreateIndex {

    private Settings settings;

    /**
     * <pre>
     * "mappings" : {
     *          "properties" : {
     *              "field1" : { "type" : "text" }
     *          }
     * }
     * </pre>
     */
    @JsonProperty("mappings")
    private Properties properties;

    /**
     * <pre>
     * "aliases" : {
     *      "alias_1" : {},
     *      "alias_2" : {
     *          "filter" : {
     *              "term" : {"user" : "kimchy" }
     *          },
     *          "routing" : "kimchy"
     *      }
     *  }
     * </pre>
     */
    @JsonProperty("aliases")
    private Map<String, Map<String, Map<String, Map<String, String>>>> aliases;


    @Data
    class Settings {
        private Integer number_of_shards;
    }

    @Data
    class Properties {
        @JsonProperty("properties")
        private Map<String, Map<String, String>> properties;
    }
}
