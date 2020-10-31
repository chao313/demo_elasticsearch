package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 检索相关(SQL语法)
 */
@FeignClient(name = "Search-SQLService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_SQLService {

}
















