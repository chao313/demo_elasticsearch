package demo.elastic.search.out.companyDeal.service;


import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FeignServiceCompany", url = "http://10.200.91.21:20199",
        configuration = FeignServiceConfig.class)
public interface FeignServiceCompany {

    @RequestMapping(value = "/", method = RequestMethod.POST)
    String getCompanyResult(@RequestBody String body);

}
