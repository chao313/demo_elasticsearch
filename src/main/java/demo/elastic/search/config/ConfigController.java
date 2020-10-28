package demo.elastic.search.config;

import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import demo.elastic.search.util.InetAddressUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/ConfigController")
@RestController
public class ConfigController {

    @Value("${default_bootstrap_servers}")
    private String default_bootstrap_server;

    @Autowired
    private StartConfig startConfig;


    @ApiOperation(value = "获取指定的es地址")
    @GetMapping(value = "/getServers")
    public Response getServers() {

        /**
         * 这里的地址是经过尝试连接的
         */
        return Response.Ok(BootstrapServersConfig.getMapUseFul());
    }

    @ApiOperation(value = "获取默认的es地址")
    @GetMapping(value = "/getDefaultServers")
    public Response getDefaultServers() {
        return Response.Ok(default_bootstrap_server);
    }


    @ApiOperation(value = "获取多字段匹配地址")
    @GetMapping(value = "/getMulti_match_fields")
    public Response getMulti_match_fields() {
        return Response.Ok(startConfig.getMulti_match_fieldsList());
    }

    @ApiOperation(value = "获取多字段默认选中匹配地址")
    @GetMapping(value = "/getMulti_match_fields_defaultList")
    public Response getMulti_match_fields_defaultList() {
        return Response.Ok(startConfig.getMulti_match_fields_defaultList());
    }

    /**
     * 配置bootstrap的地址
     */
    @Component
    public static class BootstrapServersConfig {
        /**
         * 存储有用的地址
         */
        public static Map<String, String> mapUseFul = new HashMap<>();//有用的


        @Value("#{${bootstrap_servers}}")
        private Map<String, String> map;

        @PostConstruct
        private void init() throws Exception {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String profile = entry.getKey();
                String ipAndPort = entry.getValue();
                //检查是否能够ping通 {'dev':'http://10.202.16.9:9200','dev_my':'http://10.202.16.136:9201','prod':'http://127.0.0.1:80','home':'http://39.107.236.187:7014'}
                URI uri = URI.create(ipAndPort);
                String ip = uri.getHost();
                Integer port = uri.getPort();
                if (InetAddressUtil.ping(ip, 100)) {
                    if (InetAddressUtil.isHostPortConnectable(ip, port)) {
                        //如果正常
                        mapUseFul.put(profile, ipAndPort);
                        //初始化
//                        ThreadLocalFeign.init(ipAndPort);
                    }
                }
            }
        }

        public static Map<String, String> getMapUseFul() {
            return mapUseFul;
        }
    }


}
