package demo.elastic.search.out.remove.compound;

import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.framework.Code;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/xx")
@Slf4j
public class xxx {


    @ApiImplicitParams({
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "request", value = "request", dataTypeClass = String.class, paramType = "form", allowMultiple = true)
    })
    @PostMapping(value = "/xxx")
    public Object _search(
            @RequestParam(value = "field", required = false) String field,
            @RequestParam(value = "request", required = true) String request,
            @RequestParam(name = "listFile", required = false) MultipartFile listFile
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
