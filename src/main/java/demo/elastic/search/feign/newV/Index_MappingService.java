package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 索引 mapping相关
 */
@FeignClient(name = "Index-MappingService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_MappingService {



}
















