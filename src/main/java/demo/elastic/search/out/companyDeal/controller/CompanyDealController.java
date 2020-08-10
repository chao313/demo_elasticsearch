package demo.elastic.search.out.companyDeal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.companyDeal.service.FeignServiceCompany;
import demo.elastic.search.util.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 关联接口
 */
@Slf4j
@Controller
@RequestMapping("/CompanyDealController")
public class CompanyDealController {

    @Autowired
    private FeignServiceCompany feignServiceCompany;

    @ApiOperation(value = "进行切词")
    @PostMapping(value = "/getCompanyNameResult", produces = "application/octet-stream")
    public Response getCompanyNameResult(
            @ApiParam(value = "这里上传特定格式的数据")
            @RequestParam(name = "listFile")
                    MultipartFile listFile
    ) throws Exception {

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "16");

        List<String> list = IOUtils.readLines(listFile.getInputStream());
        AtomicReference<Integer> i = new AtomicReference<>(0);
        int total = list.size();
        Map<String, Set<String>> rs = Collections.synchronizedMap(new LinkedHashMap<>(1000000));

        list.parallelStream().forEach(line -> {

            try {
                log.info("处理进度:{}/{}->{}", i.getAndSet(i.get() + 1), total, ExcelUtil.percent(i.get(), total));
                long start = System.currentTimeMillis();
                String companyResult = feignServiceCompany.getCompanyResult("{\"company\":\"" + line.trim() + "\"}");
                log.info("请求耗时:{}", System.currentTimeMillis() - start);
                JsonMapper jsonMapper = new JsonMapper();
                JsonNode jsonNode = jsonMapper.readTree(companyResult);
                String suffix = jsonNode.get("suffix").textValue();
                if (StringUtils.isBlank(suffix)) {
                    if (line.length() > 2) {
                        suffix = line.substring(line.length() - 2);
                    }
                }
                log.info("{};{}", suffix, line);
                start = System.currentTimeMillis();
                if (rs.containsKey(suffix)) {
                    rs.get(suffix).add(line);
                } else {
                    Set<String> set = Collections.synchronizedSet(new HashSet<>(1000));
                    set.add(line);
                    rs.put(suffix, set);
                }
                log.info("添加耗时:{}", System.currentTimeMillis() - start);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });
        rs.forEach((key, set) -> {
            log.info("切词结果:{}:{}:具体结果:{}", key, set.size(), set);
        });
        return Response.Ok(true);
    }


}
