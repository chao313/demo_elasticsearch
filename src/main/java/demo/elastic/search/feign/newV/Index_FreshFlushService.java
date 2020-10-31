package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 索引刷新/Flush相关
 */
@FeignClient(name = "Index-FreshFlushService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_FreshFlushService {


}
















