//package demo.elastic.search.test;
//
//import demo.elastic.search.feign.plus.MappingServicePlus;
//import demo.elastic.search.feign.plus.SearchServicePlus;
//import demo.elastic.search.out.db.mysql.service.DBService;
//import demo.elastic.search.po.request.QueryBaseRequest;
//import demo.elastic.search.po.request.Query;
//import demo.elastic.search.po.request.dsl.compound.BoolRequest;
//import demo.elastic.search.po.request.dsl.term.TermRequest;
//import demo.elastic.search.thread.ThreadPoolExecutorService;
//import demo.elastic.search.util.ExcelUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.File;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@SpringBootTest
//public class Search6254Test {
//
//    @Autowired
//    private MappingServicePlus mappingServicePlus;
//
//    @Autowired
//    private DBService dbService;
//
//    @Autowired
//    private SearchServicePlus searchServicePlus;
//
//    @Autowired
//    private ThreadPoolExecutorService threadPoolExecutorService;
//
//
//    @Test
//    public void xx() throws Exception {
//
//        String index = "tb_object_6254";
//
//        InputStream resourceAsStream = Search6254Test.class.getResourceAsStream("/简称重复333.xlsx");
//        List<List<String>> lists = ExcelUtil.readList(resourceAsStream, ExcelUtil.Type.XLSX);
//
//        for (List<String> listInExcel : lists) {
//            String F1_6254InExcel = listInExcel.get(1);
//            if (StringUtils.isBlank(F1_6254InExcel)) {
//                listInExcel.add("没有简称，不匹配");
//                continue;
//            }
//            log.info("{}", listInExcel);
//            TermRequest.TermQuery F2_6254TermRequest = TermRequest.builderQuery("F2_6254", "公司简称");
//            TermRequest.TermQuery F3_6254TermRequest = TermRequest.builderQuery("F3_6254", F1_6254InExcel);
//            List<Query> must = Arrays.asList(F2_6254TermRequest, F3_6254TermRequest);
//            BoolRequest.BoolQuery boolQuery = BoolRequest.builderQuery(must, null, null, null);
//
//            String requestBody = QueryBaseRequest.builderRequest(0, 1000, boolQuery).getRequestBody();
//            List<List<String>> listsInEs = searchServicePlus._searchToList(index, requestBody, false, null);
//            log.info("lists1:{}", listsInEs);
//            if (listsInEs.size() > 1) {
//
//                //匹配到多条
//                boolean flag = false;
//                for (List<String> vo : listsInEs) {
//                    if (vo.get(0).equalsIgnoreCase(F1_6254InExcel)) {
//                        flag = true;
//                    }
//                }
//                if (flag == true) {
//                    listInExcel.add("匹配到多条存在相同值");
//                } else {
//                    listInExcel.add("匹配到多条不存在相同值");
//                }
//
//                for (List<String> vo : listsInEs) {
//                    listInExcel.add(vo.get(0));
//                }
//            } else if ((listsInEs.size() == 1)) {
//                //匹配到多条
//                String F1_6254InES = listsInEs.get(0).get(0);
//                if (F1_6254InES.equalsIgnoreCase(F1_6254InExcel)) {
//                    listInExcel.add("匹配到单条相同");
//                    listInExcel.add(F1_6254InES);
//                } else {
//                    listInExcel.add("匹配到单条不同");
//                    listInExcel.add(F1_6254InES);
//                }
//            } else {
//                //匹配到0条
//                listInExcel.add("匹配到0条");
//            }
//        }
//
//        ExcelUtil.writeListSXSS(lists, new File("d:/result333.xlsx"));
//    }
//}