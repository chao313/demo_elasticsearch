package demo.elastic.search.po.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * {@link org.elasticsearch.search.builder.SearchSourceBuilder#searchSource()}.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class SearchSourceBuilder<T extends Query, K extends Aggs> extends ToRequestBody {


//    public static SearchSourceBuilder getInstance() {
//        return new SearchSourceBuilder<>();
//    }

    private T query;

    private K aggs;

    @ApiModelProperty(example = "0")
    private Integer from = -1;

    @ApiModelProperty(example = "10")
    private Integer size = 10;

    private List<String> _source;


    public SearchSourceBuilder<T, K> query(T query) {
        this.query = query;
        return this;
    }

    public SearchSourceBuilder<T, K> from(Integer from) {
        if (from < 0) {
            throw new IllegalArgumentException("[from] parameter cannot be negative");
        }
        this.from = from;
        return this;
    }

    public SearchSourceBuilder<T, K> size(Integer size) {
        if (size < 0) {
            throw new IllegalArgumentException("[size] parameter cannot be negative, found [" + size + "]");
        }
        this.size = size;
        return this;
    }


    public SearchSourceBuilder<T, K> source(List<String> sources) {
        this._source = sources;
        return this;
    }


    //    private List<SortBuilder<?>> sorts;

//    @JSONField(name = "aggs")
//    private Aggs aggs;

}
