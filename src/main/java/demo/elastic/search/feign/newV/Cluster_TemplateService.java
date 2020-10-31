package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 集群模板相关
 */
@FeignClient(name = "Cluster-TemplateService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Cluster_TemplateService {


}
















