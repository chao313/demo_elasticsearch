package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 集群证书相关
 */
@FeignClient(name = "Cluster-LicenseService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Cluster_LicenseService {


}
















