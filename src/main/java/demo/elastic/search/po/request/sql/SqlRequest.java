package demo.elastic.search.po.request.sql;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import demo.elastic.search.po.request.ToRequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专门用于SQL查询的语句
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class SqlRequest extends ToRequestBody {
    private String query;//查询语句
    @JsonProperty(value = "fetch_size")
    private Integer fetchSize;//获取的数量
    private Boolean columnar;//是否列式展示(支持json, yaml, cbor and smile)
    private String cursor;//光标
    private String params;//sql语句预处理

    /**
     * 构建 request 请求
     */
    private static SqlRequest builderRequest(String query, Integer fetchSize, Boolean columnar, String cursor, String params) {
        SqlRequest request = new SqlRequest(query, fetchSize, columnar, cursor, params);
        return request;
    }

    public static SqlRequest builderRequest(String query, Integer fetchSize, Boolean columnar, String params) {
        SqlRequest request = SqlRequest.builderRequest(query, fetchSize, columnar, null, params);
        return request;
    }

    public static SqlRequest builderRequest(String query, Integer fetchSize, String params) {
        SqlRequest request = SqlRequest.builderRequest(query, fetchSize, null, null, params);
        return request;
    }

    public static SqlRequest builderRequest(String query, Integer fetchSize) {
        SqlRequest request = SqlRequest.builderRequest(query, fetchSize, null, null, null);
        return request;
    }

    public static SqlRequest builderRequest(String cursor) {
        SqlRequest request = new SqlRequest(null, null, null, cursor, null);
        return request;
    }

}