package demo.elastic.search.po.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;

public class ToRequestBody {

    @SneakyThrows
    @JsonIgnore
    public String getRequestBody() {
        return new JsonMapper().writeValueAsString(this);
    }

}
