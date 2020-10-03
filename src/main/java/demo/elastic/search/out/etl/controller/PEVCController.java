package demo.elastic.search.out.etl.controller;

import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.framework.Code;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.etl.service.PEVCService;
import demo.elastic.search.util.DateUtil;
import demo.elastic.search.util.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/PEVCController")
public class PEVCController {

    @Autowired
    private PEVCService pevcService;


    @ApiOperation(value = "获取样例 EXCEL", produces = "application/octet-stream")
    @GetMapping(value = "/getDemoExcel")
    public ResponseEntity<byte[]> getDemoExcel() throws Exception {
        Resource demoExcelResource
                = AwareUtil.resourceLoader.getResource("classpath:demo/elastic/search/out/etl/resource/demoPevcMatchF6AndF26.xlsx");
        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + demoExcelResource.getFile().getName());//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(FileUtils.readFileToByteArray(demoExcelResource.getFile()), headers, statusCode);
        return response;
    }


    /**
     * 注意！这个Swagger请求有问题
     *
     * @param excelFile
     * @return
     */
    @ApiOperation(value = "上传数据,获取结果（这里上传特定格式的数据）", produces = "application/octet-stream")
    @PostMapping(value = "/matchF6AndF26OrF23")
    public Object matchF6AndF26OrF23(
            @ApiParam(value = "这里上传特定格式的数据")
            @RequestParam(name = "excelFile")
                    MultipartFile excelFile
    ) {
        try {
            List<PEVCService.SourceVo> sourceVos = pevcService.matchF6AndF26OrF23(excelFile.getInputStream());
            List<List<String>> data = new ArrayList<>();
            sourceVos.forEach(sourceVo -> {
                data.add(sourceVo.getSource());
            });
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ExcelUtil.writeListXLS(data, outputStream).close();
            HttpHeaders headers = new HttpHeaders();//设置响应
            headers.add("Content-Disposition", "attachment;filename=" + "PevcMatchF6AndF26" + DateUtil.getNow() + ".xlsx");//下载的文件名称
            HttpStatus statusCode = HttpStatus.OK;//设置响应吗
            ResponseEntity<byte[]> response = new ResponseEntity<>(outputStream.toByteArray(), headers, statusCode);
            log.info("匹配正常");
            return response;
        } catch (Exception e) {
            Response response = new Response<>();
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            log.error("[]FAIL path:{}", e.getMessage(), e);
            return response;
        }
    }

//    @ApiOperation(value = "获取样例 EXCEL")
//    @GetMapping(value = "/getDemoExcel", produces = "application/octet-stream")
//    public ResponseEntity<byte[]> getExcelByName() throws Exception {
//        Resource demoExcelResource
//                = AwareUtil.resourceLoader.getResource("classpath:demo/elastic/search/out/etl/resource/demoPevcMatchF6AndF26.xlsx");
//        HttpHeaders headers = new HttpHeaders();//设置响应头
//        headers.add("Content-Disposition", "attachment;filename=" + demoExcelResource.getFile().getName());//下载的文件名称
//        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
//        ResponseEntity<byte[]> response = new ResponseEntity<>(FileUtils.readFileToByteArray(demoExcelResource.getFile()), headers, statusCode);
//        return response;
//    }

}
