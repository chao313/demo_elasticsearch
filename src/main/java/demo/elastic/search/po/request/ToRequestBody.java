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

//    @SneakyThrows
//    @JsonIgnore
//    public String getRequestBody() {
//        JsonMapper mapper = new JsonMapper();
//        String json = mapper.writeValueAsString(this);
//        String result = StringEscapeUtils.unescapeJavaScript(json);//去除双\\
//        return result;
//    }
}
