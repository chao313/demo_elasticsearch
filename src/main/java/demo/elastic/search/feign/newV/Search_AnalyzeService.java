package demo.elastic.search.feign.newV;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.analyze.AnalyzeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 检索相关(分词器相关)
 */
@FeignClient(name = "Search-AnalyzeService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_AnalyzeService {


}
















