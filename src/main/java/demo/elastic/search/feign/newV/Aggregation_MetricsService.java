package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 指标聚合相关
 */
@FeignClient(name = "Aggregation-MetricsService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Aggregation_MetricsService {


}
















