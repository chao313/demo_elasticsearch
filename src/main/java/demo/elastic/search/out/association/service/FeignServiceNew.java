package demo.elastic.search.out.association.service;


import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "windNew", url = "http://10.202.16.136:32114",
        configuration = FeignServiceConfig.class)
public interface FeignServiceNew {

    /**
     * @param companyName
     * @return
     */
    @RequestMapping(value = "/getWindCodeByCompanyNameOrCrawlNew", method = RequestMethod.GET)
    String getWindCodeByCompanyNameOrCrawlNew(
            @RequestParam("companyName") String companyName,
            @RequestParam("tableName") String tableName,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("source") String source,
            @RequestParam("filter") String filter
    );

}
