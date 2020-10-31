package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 索引 别名相关
 */
@FeignClient(name = "Index-AliasService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_AliasService {


}
















