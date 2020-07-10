package demo.elastic.search.out.etl.service;

import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.out.etl.dao.DAO0088;
import demo.elastic.search.out.etl.vo.OCFullNameMapShortNameAndCode;
import demo.elastic.search.out.etl.vo.Tb0088Vo;
import demo.elastic.search.util.CSVUtil;
import demo.elastic.search.util.ExcelUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据F6_0088定位数据
 */
@Service
@Slf4j
public class PEVCService {

    @Autowired
    private DAO0088 dao0088;


    /**
     * 根据输入流,返回匹配结果
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public List<SourceVo> matchF6AndF26OrF23(InputStream inputStream) throws Exception {
        List<SourceVo> sourceVos = this.initSourceVoFromExcelAndOCFullNameMapShortName(inputStream);
        sourceVos.forEach(sourceVo -> {
            if (null == sourceVo.F26_0088) {
                log.info("无法定位F26_0088的数据:{}", sourceVo.getSource());
            }
        });
        int i = 0;
        int total = sourceVos.size();
        for (SourceVo sourceVo : sourceVos) {
            log.info("写入进度:{}/{}->{}", i++, total, ExcelUtil.percent(i, total));
            List<Tb0088Vo> result = new ArrayList<>();
            boolean flag = false;
            log.info("当前处理:{}", sourceVo);
            List<Tb0088Vo> tb0088Vos = dao0088.query0088ByF6(sourceVo.F6_0088, "1m");
            if (tb0088Vos.size() == 0) {
                log.info("当前F6无法查出数据:{}", sourceVo.getSource());
                sourceVo.getSource().add("当前F6无法查出数据");
            } else {
                log.info("开始遍历数据库中的数据，寻找最优的匹配");
                for (Tb0088Vo tb0088Vo : tb0088Vos) {
                    if (null != tb0088Vo.getF26_0088() && null != tb0088Vo.getF2_0088()) {
                        if (tb0088Vo.getF26_0088().equalsIgnoreCase(sourceVo.F26_0088)) {
                            if (tb0088Vo.getF2_0088().equalsIgnoreCase(sourceVo.nameNotEq)) {
                                log.info("名称完成一致 抛弃 error:{}$$$${}$$$${}$$$${}$$$${}", sourceVo.nameNotEq, sourceVo.F6_0088, sourceVo.country, sourceVo.province, sourceVo.F26_0088);
                                sourceVo.getSource().add("名称完成一致抛弃");
                            } else {
                                log.info("success:{}$$$${}$$$${}$$$${}", sourceVo.getSource(), tb0088Vo.getF1_0088(), tb0088Vo.getF2_0088(), tb0088Vo.getF26_0088());
                                result.add(tb0088Vo);
                                flag = true;
                            }
                        } else {
                            log.info("数据库中无法匹配:sourceVo{},tb0088Vo:{}", sourceVo, tb0088Vo);
                        }
                    }
                }
                if (flag == false) {
                    sourceVo.getSource().add("数据库根据编号可以查询,但是不匹配F26");
                    log.info("没有符合条件的数据 error:{}$$$${}$$$${}$$$${}$$$${}", sourceVo.nameNotEq, sourceVo.F6_0088, sourceVo.country, sourceVo.province, sourceVo.F26_0088);
                }
            }
            if (result.size() == 1) {
                sourceVo.getSource().add("单值匹配到数据");
                result.forEach(vo -> {
                    log.info("单值匹配到数据:value:{}$$$${}$$$${}$$$${}", sourceVo.getSource(), vo.getF1_0088(), vo.getF2_0088(), vo.getF26_0088());
                    sourceVo.getSource().add(vo.getF1_0088());
                    sourceVo.getSource().add(vo.getF2_0088());
                    sourceVo.getSource().add(vo.getF26_0088());
                });
            } else if (result.size() > 1) {
                sourceVo.getSource().add("多值匹配到数据");
                result.forEach(vo -> {
                    log.info("多值匹配到数据:value:{}$$$${}$$$${}$$$${}", sourceVo.getSource(), vo.getF1_0088(), vo.getF2_0088(), vo.getF26_0088());
                    sourceVo.getSource().add(vo.getF1_0088());
                    sourceVo.getSource().add(vo.getF2_0088());
                    sourceVo.getSource().add(vo.getF26_0088());
                });
            }

        }
        return sourceVos;
    }

    /**
     * 表格
     */
    @Data
    public class SourceVo {
        String nameNotEq;//名称
        String F6_0088;//编号
        String country;//国家
        String province;//地区 州名(美国)
        String F26_0088;//->最终处理的数据
        List<String> source;//元数据

        public SourceVo(String nameNotEq, String f6_0088, String country, String province, List<String> source) {
            this.nameNotEq = nameNotEq;
            F6_0088 = f6_0088;
            this.country = country;
            this.province = province;
            this.source = source;
        }
    }

    /**
     * 配置
     *
     * @param fullNameMapShortNameAndCodeResource
     * @return
     * @throws IOException
     */
    private Map<String, OCFullNameMapShortNameAndCode> getFullNameMapShortNameAndCode(Resource fullNameMapShortNameAndCodeResource) throws IOException {
        Map<Integer, List<String>> OCFullNameShortNameAndCodeResourceMap = CSVUtil.read(fullNameMapShortNameAndCodeResource.getFile());

        List<OCFullNameMapShortNameAndCode> ocFullNameMapShortNameAndCodes = new ArrayList<>();
        OCFullNameShortNameAndCodeResourceMap.forEach((index, list) -> {
            if (index >= 0) {
                if (list.size() >= 4) {
                    OCFullNameMapShortNameAndCode ocFullNameMapShortNameAndCode = new OCFullNameMapShortNameAndCode();
                    ocFullNameMapShortNameAndCode.setFullName(list.get(0).toUpperCase().trim());
                    ocFullNameMapShortNameAndCode.setShortName(list.get(1).toUpperCase().replace("US_", ""));
                    ocFullNameMapShortNameAndCode.setChineseName(list.get(2));
                    ocFullNameMapShortNameAndCode.setF23_0088(list.get(3));
                    if (list.size() >= 5) {
                        ocFullNameMapShortNameAndCode.setLength(list.get(4));
                    }
                    if (list.size() >= 6) {
                        ocFullNameMapShortNameAndCode.setF26_0088(list.get(5));
                    }
                    if (list.size() >= 7) {
                        ocFullNameMapShortNameAndCode.setIsPevc(list.get(6));
                    }
                    log.info("ocFullNameMappShortNameAndCode:{}", ocFullNameMapShortNameAndCode);
                    ocFullNameMapShortNameAndCodes.add(ocFullNameMapShortNameAndCode);
                }
            }
        });
        Map<String, OCFullNameMapShortNameAndCode> result = new HashMap<>();
        ocFullNameMapShortNameAndCodes.forEach(ocFullNameMapShortNameAndCode -> {
            result.put(ocFullNameMapShortNameAndCode.getFullName(), ocFullNameMapShortNameAndCode);
        });

        return result;
    }

    /**
     * 根据 全称和简称匹配及源数据,格式化成为标准数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private List<SourceVo> initSourceVoFromExcelAndOCFullNameMapShortName(InputStream inputStream) throws IOException {
        Resource FullNameMappShortNameAndCodeResource
                = AwareUtil.resourceLoader.getResource("classpath:demo/elastic/search/out/etl/resource/OCFullNameMappShortName.csv");
        Map<String, OCFullNameMapShortNameAndCode> fullNameMapShortNameAndCodesMap
                = this.getFullNameMapShortNameAndCode(FullNameMappShortNameAndCodeResource);

        /**处理数据源*/
        List<SourceVo> sourceVos = new ArrayList<>();
        List<List<String>> lists = ExcelUtil.readList(inputStream, ExcelUtil.Type.XLSX);
        lists.forEach(list -> {
            String name = list.get(0).trim();
            String F6_0088 = list.get(1).trim();
            String country = list.get(2).trim();
            String province = list.get(3).trim();
            SourceVo sourceVo = new SourceVo(name, F6_0088, country, province, list);
            sourceVos.add(sourceVo);
        });

        sourceVos.forEach(sourceVo -> {
            if (!sourceVo.country.equalsIgnoreCase("United States")) {
                log.info("如果不是美国,使用country获取F26");
                OCFullNameMapShortNameAndCode ocFullNameMapShortNameAndCode
                        = fullNameMapShortNameAndCodesMap.get(sourceVo.country.toUpperCase());
                if (ocFullNameMapShortNameAndCode == null) {
                    log.error("根据国家无法在配置中查询数据");
                } else {
                    sourceVo.setF26_0088(ocFullNameMapShortNameAndCode.getF26_0088());
                }
            } else {
                log.info("如果是不是美国,使用province获取F26");
                OCFullNameMapShortNameAndCode ocFullNameMapShortNameAndCode
                        = fullNameMapShortNameAndCodesMap.get(sourceVo.province.toUpperCase());
                if (ocFullNameMapShortNameAndCode == null) {
                    log.error("根据国家无法在配置中查询数据");
                } else {
                    sourceVo.setF26_0088(ocFullNameMapShortNameAndCode.getF26_0088());
                }
            }
        });

        return sourceVos;
    }

}
