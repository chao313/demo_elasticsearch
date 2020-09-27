package demo.elastic.search.feign.plus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.util.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mapping Service的加强版
 */
@Service
public class MappingServicePlus implements MappingService {

    @Resource
    private MappingService mappingService;

    @Override
    public String get(String index) {
        return mappingService.get(index);
    }

    @Override
    public String get(String index, String fieldName) {
        return mappingService.get(index, fieldName);
    }

    @Override
    public String put(String index, String body) {
        return mappingService.put(index, body);
    }

    @Override
    public String put(String index, String type, String body) {
        return mappingService.put(index, type, body);
    }

    /**
     * 获取index的field的names
     * 7 的版本:
     * <pre>
     * {
     *     "bank":{
     *         "mappings":{
     *             "properties":{
     *                 "account_number":{
     *                     "type":"long"
     *                 }
     *             }
     *         }
     *     }
     * }
     * </pre>
     * <p>
     * 2的版本
     * <pre>
     * {
     *     "comstore_tb_object_0088_v2":{
     *         "mappings":{
     *             "comstore_tb_object_0088":{
     *                 "_all":{
     *                     "enabled":false
     *                 },
     *                 "properties":{
     *                     "ES_MOD_TIME":{
     *                         "type":"date",
     *                         "format":"yyyy-MM-dd HH:mm:ss"
     *                     }
     *                 }
     *             }
     *         }
     *     }
     * }
     * </pre>
     */
    public List<String> getFieldNamesList(String index) {
        String result = mappingService.get(index);
        JSONObject properties = null;
        switch (Bootstrap.getInUseVersion()) {
            case TWO: {
                properties = JSONUtil.getByKeyAndLevel(JSON.parseObject(result), 4, 0, "properties");
            }
            break;
            case SEVEN: {
                properties = JSONUtil.getByKeyAndLevel(JSON.parseObject(result), 3, 0, "properties");
            }
            break;
        }
        List<String> resultNames = new ArrayList<>(properties.keySet());//存放name的地方
        Collections.sort(resultNames, (o1, o2) -> o1.compareTo(o2));//排序
        return resultNames;
    }
}
