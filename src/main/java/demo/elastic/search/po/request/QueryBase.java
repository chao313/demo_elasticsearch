package demo.elastic.search.po.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.SneakyThrows;

@Data
//基础查询
public class QueryBase extends ToRequestBody {

    @ApiModelProperty(example = "0")
    @JSONField(name = "from")
    private Integer from;

    @ApiModelProperty(example = "10")
    @JSONField(name = "size")
    private Integer size;

}
