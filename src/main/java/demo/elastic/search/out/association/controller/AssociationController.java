package demo.elastic.search.out.association.controller;

import com.google.common.io.LineReader;
import demo.elastic.search.out.association.service.WindCodeService;
import demo.elastic.search.util.DateUtil;
import demo.elastic.search.util.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 关联接口
 */
@Slf4j
@Controller
@RequestMapping("/AssociationController")
public class AssociationController {

    @Autowired
    private WindCodeService windCodeService;


    @ApiOperation(value = "根据名称获取匹配结果")
    @PostMapping(value = "/getWindCodeByCompanyNameNew", produces = "application/octet-stream")
    public ResponseEntity<byte[]> getWindCodeByCompanyNameNew(
            @ApiParam(value = "这里上传特定格式的数据")
            @RequestParam(name = "listFile")
                    MultipartFile listFile
    ) throws Exception {
        String tableName = "WIND.TB_OBJECT_0088";
        String fieldName = "F8_0088";
        String source = "HWZL";
        String filter = "area:0,1,2,3,4,5";
        List<String> list = IOUtils.readLines(listFile.getInputStream());
        Map<String, Set<String>> result = Collections.synchronizedMap(new LinkedHashMap<>());
        AtomicReference<Integer> i = new AtomicReference<>(0);
        int total = list.size();

        list.parallelStream().forEach(line -> {
            log.info("处理进度:{}/{}->{}", i.getAndSet(i.get() + 1), total, ExcelUtil.percent(i.get(), total));
            Set<String> F1_0088s = windCodeService.getWindCodeByCompanyNameNew(line.trim(), tableName, fieldName, source, filter);
            result.put(line.trim(), F1_0088s);
        });
        log.info("关联正常");
        List<List<String>> resultList = new ArrayList<>();
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        result.forEach((line, set) -> {
            List<String> tmp = new ArrayList<>();
            tmp.add(line);
            tmp.addAll(set);
            resultList.add(tmp);
        });
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelUtil.writeListXLS(resultList, outputStream).close();
        HttpHeaders headers = new HttpHeaders();//设置响应
        headers.add("Content-Disposition", "attachment;filename=" + "WindCodeByCompanyNameNew_" + DateUtil.getNow() + ".xlsx");//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(outputStream.toByteArray(), headers, statusCode);
        log.info("匹配正常");
        return response;
    }

    @ApiOperation(value = "根据名称获取匹配结果")
    @PostMapping(value = "/getWindCodeByCompanyNameNew/list", produces = "application/octet-stream")
    public ResponseEntity<byte[]> getWindCodeByCompanyNameNew_list(
            @ApiParam(value = "这里上传特定格式的数据")
            @RequestParam(name = "listFile")
                    MultipartFile listFile
    ) throws Exception {
        String tableName = "WIND.TB_OBJECT_0088";
        String fieldName = "F8_0088";
        String source = "HWZL";
        String filter = "area:0,1,2,3,4,5";
        List<String> list = IOUtils.readLines(listFile.getInputStream());
        AtomicReference<Integer> i = new AtomicReference<>(0);
        int total = list.size();
        List<List<String>> resultList = new ArrayList<>();
        list.forEach(line -> {
            log.info("处理进度:{}/{}->{}", i.getAndSet(i.get() + 1), total, ExcelUtil.percent(i.get(), total));
            Set<String> F1_0088s = windCodeService.getWindCodeByCompanyNameNew(line.trim(), tableName, fieldName, source, filter);
            List<String> tmp = new ArrayList<>();
            tmp.add(line);
            tmp.addAll(F1_0088s);
            resultList.add(tmp);
        });
        log.info("关联正常");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExcelUtil.writeListXLS(resultList, outputStream).close();
        HttpHeaders headers = new HttpHeaders();//设置响应
        headers.add("Content-Disposition", "attachment;filename=" + "WindCodeByCompanyNameNew_" + DateUtil.getNow() + ".xlsx");//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(outputStream.toByteArray(), headers, statusCode);
        log.info("匹配正常");
        return response;
    }


}
