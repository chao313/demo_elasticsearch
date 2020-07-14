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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/xx")
@Slf4j
public class xxx {


    @ApiImplicitParams(
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "file", paramType = "form")
    )
    public Object _search(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(defaultValue = "F6_0088", value = "下面的文件中需要匹配的值")
            @RequestParam(value = "field") String field,
            @RequestParam(value = "values") List<String> values,
            @RequestBody SearchTermsRequest searchTermsRequest,
            HttpServletRequest r
    ) {
        try {
            InputStream inputStream = AwareUtil.resourceLoader.getResource("classpath:xx").getInputStream();
            List<String> list = IOUtils.readLines(inputStream);
            Body body = searchTermsRequest.getBody();
        } catch (Exception e) {
            Response response = new Response<>();
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            log.error("发生异常:{}", e.getMessage(), e);
            return response;
        }
        return null;
    }

}
