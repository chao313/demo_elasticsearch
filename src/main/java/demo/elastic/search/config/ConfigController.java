package demo.elastic.search.config;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/ConfigController")
@RestController
public class ConfigController {

    @ApiOperation(value = "获取指定的kafka地址")
    @GetMapping(value = "/getKafkaBootstrapServers")
    public Object getKafkaBootstrapServers() {

        /**
         * 这里的地址是经过拦截的
         */
        return Bootstrap.IN_USE;
    }

}
