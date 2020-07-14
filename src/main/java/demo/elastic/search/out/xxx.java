package demo.elastic.search.out;

import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.framework.Code;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.Body;
import demo.elastic.search.vo.SearchTermsRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/xx")
@Slf4j
public class xxx {


    @ApiImplicitParams({
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "values", value = "values", dataTypeClass = List.class, paramType = "form", allowMultiple = true),
            @ApiImplicitParam(name = "body", value = "body", dataTypeClass = Body.class, paramType = "form"),
    })
    @PostMapping(value = "/xxx")
    public Object _search(
            @RequestParam(value = "field") String field,
            @RequestParam Body body,
            @RequestParam(value = "values") List<String> values,
//            @RequestParam(name = "listFile")
//                    MultipartFile listFile,
            HttpServletRequest r
    ) {
        try {
            InputStream inputStream = AwareUtil.resourceLoader.getResource("classpath:xx").getInputStream();
            List<String> list = IOUtils.readLines(inputStream);

        } catch (Exception e) {
            Response response = new Response<>();
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            log.error("发生异常:{}", e.getMessage(), e);
            return response;
        }
        return "ok";
    }
}
