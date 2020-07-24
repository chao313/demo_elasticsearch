package demo.elastic.search.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import demo.elastic.search.po.request.index.doc.reindex.ReIndexRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义转换器
 */
@Slf4j
public class MyJackSonConverter extends StdConverter<ReIndexRequest, ReIndexRequest> {


    /**
     * 这里还不是很明白 -> 前后一致可以解决问题
     *
     * @param reIndexRequest
     * @return
     */
    @SneakyThrows
    @Override
    public ReIndexRequest convert(ReIndexRequest reIndexRequest) {
        ReIndexRequest result = this.deal(reIndexRequest);
        return result;

    }

    private ReIndexRequest deal(ReIndexRequest reIndexRequest) throws JsonProcessingException {
        JsonMapper mapper = new JsonMapper();
        String body = mapper.writeValueAsString(reIndexRequest);
        JsonNode jsonNode = mapper.readTree(body);
        AtomicReference<Integer> i = null;
        do {
            /**
             * 当i>0 持续移除
             */
            i = new AtomicReference<>(0);
            this.loop(jsonNode, i);
        } while (i.get() > 0);
        /**
         * ! 在插件里不能调用 JsonMapper 转换器 -> 会无限调用自己！
         */
        //ReIndexRequest result = mapper.readValue(jsonNode.toString(), ReIndexRequest.class);
        ReIndexRequest result = JSON.toJavaObject(JSON.parseObject(jsonNode.toString()), ReIndexRequest.class);
        return result;

    }

    /**
     * 遍历移除value为{}的field
     */
    private void loop(JsonNode jsonNode, AtomicReference<Integer> i) {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        fields.forEachRemaining(field -> {
            JsonNode value = field.getValue();
            if (null != value) {
                if ("{}".equalsIgnoreCase(value.toString())) {
                    fields.remove();
                    i.set(i.get() + 1);
                } else {
                    loop(value, i);
                }
            }
        });
    }


}