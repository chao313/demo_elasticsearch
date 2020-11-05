package demo.elastic.search.controller.helper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import demo.elastic.search.config.StartConfig;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import demo.elastic.search.util.HttpRequestUtils;
import demo.elastic.search.util.InetAddressUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/ConfigController")
@RestController
public class ConfigController {

    @Value("${default_bootstrap_servers}")
    private String default_bootstrap_server;


    @Value("#{'${role.vip}'.split(',')}")
    private List<String> roleVipIps;//vip角色的ip(拥有导出功能的)

    @Value("#{'${role.admin}'.split(',')}")
    private List<String> roleAdminIps;//admin角色的ip

    @Value("#{'${role.supAdmin}'.split(',')}")
    private List<String> roleSupAdminIps;//supAdmin角色的ip

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

    @ApiOperation(value = "获取demo")
    @GetMapping(value = "/getDemoStr")
    public Response getDemoStr() {
        return Response.Ok(startConfig.getDemoStr());
    }

    @ApiOperation(value = "获取ip的角色，获取请求段的ip")
    @GetMapping(value = "/getRoleAdmin")
    public Response getRoleAdmin(HttpServletRequest httpServletRequest) {
        String ip = HttpRequestUtils.getRealRequestIp(httpServletRequest);

        Response response;
        if (roleSupAdminIps.contains(ip)) {
            //如果是supAdmin角色
            response = Response.Ok(Role.SupAdmin);
        } else if (roleAdminIps.contains(ip)) {
            //如果是admin角色
            response = Response.Ok(Role.Admin);
        } else if (roleVipIps.contains(ip)) {
            //如果是vip
            response = Response.Ok(Role.Vip);
        } else {
            //如果不是admin 和 supAdmin -> 默认为游客
            response = Response.Ok(Role.Visitor);
        }
        response.setMsg(ip);
        return response;
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    enum Role {
        Visitor("visitor", 0, Arrays.asList()),
        Vip("Vip", 30, Arrays.asList(10, 50, 100, 500, 1000)),
        Admin("admin", 50, Arrays.asList(10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000)),
        SupAdmin("supAdmin", 100, Arrays.asList(10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 200000, 500000, 1000000, -1)),
        ;

        public String getRoleName() {
            return roleName;
        }

        public Integer getLevel() {
            return level;
        }

        public List<Integer> getOutPutLevel() {
            return outPutLevel;
        }

        private String roleName;
        private Integer level;//角色级别
        private List<Integer> outPutLevel;//导出size

        Role(String roleName, Integer level, List<Integer> outPutLevel) {
            this.roleName = roleName;
            this.level = level;
            this.outPutLevel = outPutLevel;
        }

        /**
         * 根据角色名称 获取角色
         */
        public static Role getRoleByRoleName(String roleName) {
            for (Role role : Role.values()) {
                if (role.getRoleName().equals(roleName)) {
                    return role;
                }
            }
            return null;
        }
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
