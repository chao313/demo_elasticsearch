package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.feign.enums.FormatEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 集群索引相关
 */

@FeignClient(name = "Cluster-IndexService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Cluster_IndexService {

}
















