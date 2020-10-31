package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * Index CRUD 相关
 */
@FeignClient(name = "Index-CRUDService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_CRUDService {



}
















