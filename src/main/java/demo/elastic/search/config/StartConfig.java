package demo.elastic.search.config;

import demo.elastic.search.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * 启动配置
 */
@Component
public class StartConfig {

    //注入 多字段匹配
    @Value(value = "classpath:data/multi_match_fields.txt")
    private Resource multi_match_fields;

    //注入 多字段匹配(默认选中的)
    @Value(value = "classpath:data/multi_match_fields_default.txt")
    private Resource multi_match_fields_default;

    //注入 demo
    @Value(value = "classpath:data/demo.txt")
    private Resource demo;

    //访问的白名单
    @Value("#{'${white.list}'.split(',')}")
    private List<String> whiteList;

    private List<String> multi_match_fieldsList;

    private List<String> multi_match_fields_defaultList;

    private String demoStr;

    @PostConstruct
    public void init() throws IOException {
        multi_match_fieldsList = IOUtils.readLines(multi_match_fields.getInputStream());
        multi_match_fields_defaultList = IOUtils.readLines(multi_match_fields_default.getInputStream());
        demoStr = IOUtils.toString(demo.getInputStream(), "UTF-8");
    }

    public List<String> getMulti_match_fieldsList() {
        return multi_match_fieldsList;
    }

    public List<String> getMulti_match_fields_defaultList() {
        return multi_match_fields_defaultList;
    }

    public String getDemoStr() {
        return demoStr;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }
}