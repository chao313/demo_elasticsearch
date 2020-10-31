package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 通道聚合相关
 */
@FeignClient(name = "Aggregation-PipelineService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Aggregation_PipelineService {


}



