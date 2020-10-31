package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 集群XPack相关
 */
@FeignClient(name = "Cluster-XPackService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Cluster_XPackService {


}
















