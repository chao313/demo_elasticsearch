package demo.elastic.search.feign.plus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.thread.ThreadLocalFeign;
import demo.elastic.search.util.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mapping Service的加强版
 */
@Service
public class MappingServicePlus {
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
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String result = mappingService.get(index);
        JSONObject properties = null;
        switch (Bootstrap.getBootstrapByUrl(ThreadLocalFeign.getES_HOST()).getVersion()) {
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
        Collections.sort(resultNames, (o1, o2) -> {
            Integer key1 = null;
            if (o1.toString().matches("F\\d{1,}_\\d{1,}")) {
                key1 = Integer.valueOf(o1.toString().replace("F", "").replace("_", ""));
            }
            Integer key2 = null;
            if (o2.toString().matches("F\\d{1,}_\\d{1,}")) {
                key2 = Integer.valueOf(o2.toString().replace("F", "").replace("_", ""));
            }
            if (key1 != null && key2 != null) {
                return key1.compareTo(key2);
            } else {
                return o1.toString().compareTo(o2.toString());
            }
        });//排序
        return resultNames;
    }
}
