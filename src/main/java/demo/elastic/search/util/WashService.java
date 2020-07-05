package demo.elastic.search.util;

import cn.com.wind.OverseaDataHandler.common.data.DataPool;
import cn.com.wind.OverseaDataHandler.tool.vo.TB_OBJECT_0099;
import cn.com.wind.OverseaDataHandler.util.vo.*;
import cn.com.wind.bussiness.structure.base.WarnDealService;
import cn.com.wind.bussiness.structure.base.WarnInfos;
import cn.com.wind.bussiness.util.CollectionUtils;
import cn.com.wind.bussiness.util.DateUtils;
import cn.com.wind.bussiness.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义通用清洗
 */
@Service
public class WashService {

    private static Logger LOGGER = LoggerFactory.getLogger(WashService.class);

    public final static String PREFIX = "WIND.TB_OBJECT";
    public final static String DOWNLOADFILEURLS = "downLoadFileUrls";
    public final static String F23_0088_FIRST_TMP = "F23_0088_first_tmp";
    public final static String F23_0088_SECOND_TMP = "F23_0088_second_tmp";


    public final static String TB_OBJECT_0088 = "WIND.TB_OBJECT_0088";
    public final static String TB_OBJECT_6176 = "WIND.TB_OBJECT_6176";
    public final static String TB_OBJECT_6177 = "WIND.TB_OBJECT_6177";
    public final static String TB_OBJECT_6178 = "WIND.TB_OBJECT_6178";
    public final static String TB_OBJECT_6254 = "WIND.TB_OBJECT_6254";
    public final static String TB_OBJECT_6316 = "WIND.TB_OBJECT_6316";
    public final static String TB_OBJECT_6315 = "WIND.TB_OBJECT_6315";
    public final static String TB_OBJECT_6428 = "WIND.TB_OBJECT_6428";
    public final static String TB_OBJECT_6532 = "WIND.TB_OBJECT_6532";
    public final static String TB_OBJECT_6768 = "WIND.TB_OBJECT_6768";
    public final static String TB_OBJECT_6725 = "WIND.TB_OBJECT_6725";
    public final static String TB_OBJECT_6600 = "WIND.TB_OBJECT_6600";
    public final static String TB_OBJECT_6489 = "WIND.TB_OBJECT_6489";
    public final static String TB_OBJECT_6843 = "WIND.TB_OBJECT_6843";


    @Autowired
    private WarnDealService warnDealService;

    /**
     * 处理指定的日期格式的数据
     * eg. F12_0088=M/d/yyyy
     * dealField.put("F12_0088", "M/d/yyyy");
     * dealField.put("F6_6768", "M/d/yyyy");
     * dealField.put("F5_6316", "yyyy-MM-dd HH:mm:ss");
     *
     * @param root
     * @param dealField
     * @return
     */
    public JSONObject dateWash(JSONObject root, Map<String, String> dealField) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                dealField.forEach((filed, format) -> {
                    if (filed.equalsIgnoreCase(key)) {
                        try {
                            //直接转格式，异常不落盘
                            String valueWashed = this.formatDateStr(value.toString(), format,
                                    DateUtils.FORMAT_DATE_TO_STRING_YYYYMMDD);
                            root.put(key, valueWashed);
                        } catch (Exception e) {
                            LOGGER.info("日期转格式失败:{},format:{}", value, format);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.dateWash((JSONObject) value, dealField);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.dateWash((JSONObject) jsonObjet, dealField);
                });
            }
        }
        return root;
    }

    /**
     * 处理通过F2_0003来获取F3_0003的数据
     * 1.存储字段
     * 2.来源字段
     * 3.所属类型
     * <p>
     * 这边的来源字段一般是属于当前的node，只会在当前节点查找
     *
     * @param root
     * @param wash0003s
     * @param warnInfo
     * @param flag      是否落盘
     * @return
     */
    public JSONObject washF3_0003ByF2_0003(JSONObject root, List<Wash0003> wash0003s, WarnInfos warnInfo, boolean flag) {
        //字段的补充注入
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                final JSONObject json0088Object = json0088OArray.getJSONObject(0);
                if (null != json0088Object) {
                    wash0003s.forEach(wash0003 -> {
                        if (wash0003.getTargetField().endsWith("_0088")) {
                            if (!json0088Object.containsKey(wash0003.getTargetField())) {
                                //补全0088的字段
                                json0088Object.put(wash0003.getTargetField(), "");
                            }
                        } else {
                            String tableSuffix = wash0003.getTargetField().substring(wash0003.getTargetField().indexOf("_"));
                            if (json0088Object.containsKey(PREFIX + tableSuffix)) {
                                JSONObject jsonObject = json0088Object.getJSONObject(PREFIX + tableSuffix);
                                if (jsonObject.containsKey("data")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    jsonArray.forEach(json -> {
                                        if (json instanceof JSONObject) {
                                            if (!((JSONObject) json).containsKey(wash0003.getTargetField())) {
                                                ((JSONObject) json).put(wash0003.getTargetField(), "");
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }
        return this.washF3_0003ByF2_0003Loop(root, wash0003s, warnInfo, flag);

    }


    /**
     * @param root
     * @param N            取前多少位
     * @param defaultValue 默认值（查询不到的情形下）
     * @return 弃用原因: 地区码可能会混淆，需要指定前缀
     */
    @Deprecated
    public JSONObject washF23_0088(JSONObject root, Integer N, String defaultValue) {

        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");

            if (json0088OArray.size() == 1) {
                final JSONObject json0088Object = json0088OArray.getJSONObject(0);
                if (null != json0088Object) {
                    boolean flag = false;//判断是否已经赋值
                    if (json0088Object.containsKey(F23_0088_FIRST_TMP) && json0088Object.containsKey(F23_0088_SECOND_TMP)) {
                        String F23_0088_FIRST_TMP_VALUE = json0088Object.getString(F23_0088_FIRST_TMP);
                        String F23_0088_SECOND_TMP_VALUE = json0088Object.getString(F23_0088_SECOND_TMP);
                        if (StringUtils.isNotEmpty(F23_0088_FIRST_TMP_VALUE)
                                && StringUtils.isNotEmpty(F23_0088_SECOND_TMP_VALUE)) {
                            String F23_0088_VALUE = this.getCityCode(F23_0088_FIRST_TMP_VALUE, F23_0088_SECOND_TMP_VALUE, N, defaultValue);
                            json0088Object.put("F23_0088", F23_0088_VALUE);
                            flag = true;
                            /**
                             * 生产时 需要取消注释
                             */
                            json0088Object.remove(F23_0088_FIRST_TMP);
                            json0088Object.remove(F23_0088_SECOND_TMP);
                        } else {
                            LOGGER.info("F23_0088_FIRST_TMP_VALUE is {} F23_0088_SECOND_TMP_VALUE is {} ",
                                    F23_0088_FIRST_TMP_VALUE, F23_0088_SECOND_TMP_VALUE);
                        }
                    } else {
                        LOGGER.info("没有 F23_0088_FIRST_TMP json0088Object is {} ", json0088Object);
                    }
                    if (!flag) {
                        json0088Object.put("F23_0088", defaultValue);
                    }

                }
                /**
                 * 生产时 需要取消注释
                 */
                json0088Object.remove(F23_0088_FIRST_TMP);
                json0088Object.remove(F23_0088_SECOND_TMP);
            }

        }
        return root;
    }

    /**
     * @param root
     * @param N            取前多少位
     * @param defaultValue 默认值（查询不到的情形下）
     * @param startValue   指定前缀
     * @return
     */
    public JSONObject washF23_0088AndLimitStart(JSONObject root, Integer N, String defaultValue, String startValue) {

        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");

            if (null != json0088OArray && json0088OArray.size() == 1) {
                final JSONObject json0088Object = json0088OArray.getJSONObject(0);
                if (null != json0088Object) {
                    boolean flag = false;//判断是否已经赋值
                    if (json0088Object.containsKey(F23_0088_FIRST_TMP) && json0088Object.containsKey(F23_0088_SECOND_TMP)) {
                        String F23_0088_FIRST_TMP_VALUE = json0088Object.getString(F23_0088_FIRST_TMP);
                        String F23_0088_SECOND_TMP_VALUE = json0088Object.getString(F23_0088_SECOND_TMP);
                        if (StringUtils.isNotEmpty(F23_0088_FIRST_TMP_VALUE)
                                && StringUtils.isNotEmpty(F23_0088_SECOND_TMP_VALUE)) {
                            String F23_0088_VALUE = this.getCityCode(F23_0088_FIRST_TMP_VALUE, F23_0088_SECOND_TMP_VALUE, N, defaultValue, startValue);
                            json0088Object.put("F23_0088", F23_0088_VALUE);
                            flag = true;
                            /**
                             * 生产时 需要取消注释
                             */
                            json0088Object.remove(F23_0088_FIRST_TMP);
                            json0088Object.remove(F23_0088_SECOND_TMP);
                        } else {
                            LOGGER.info("F23_0088_FIRST_TMP_VALUE is {} F23_0088_SECOND_TMP_VALUE is {} ",
                                    F23_0088_FIRST_TMP_VALUE, F23_0088_SECOND_TMP_VALUE);
                        }
                    } else {
                        LOGGER.info("没有 F23_0088_FIRST_TMP json0088Object is {} ", json0088Object);
                    }
                    if (!flag) {
                        json0088Object.put("F23_0088", defaultValue);
                    }

                }
                /**
                 * 生产时 需要取消注释
                 */
                json0088Object.remove(F23_0088_FIRST_TMP);
                json0088Object.remove(F23_0088_SECOND_TMP);
            }

        }
        return root;
    }

    /**
     * 用于清洗非业务字段（这里不包含0088）
     * <p>
     * 如果业务字段全部为0 ， 就移除
     * !!! 记住是非业务字段！！！
     *
     * @return
     */
    public JSONObject washBussField(JSONObject root, List<WashBussFieldVo> washBussFieldVos) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
            }
        }
        final JSONObject json0088ObjectClone = json0088Object;
        if (null != json0088ObjectClone) {
            washBussFieldVos.forEach(washBussFieldvo -> {
                String belongToTable = washBussFieldvo.getBelongToTable();
                Set<String> notBussFields = washBussFieldvo.getNotBussFields();
                if (json0088ObjectClone.containsKey(belongToTable)) {
                    JSONObject jsonObject = json0088ObjectClone.getJSONObject(belongToTable);
                    if (jsonObject.containsKey("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONArray clondeJsonArray = JSONArray.parseArray(jsonArray.toJSONString());
                        clondeJsonArray.forEach(json -> {
                            if (json instanceof JSONObject) {
                                Set<String> cloneFieldsInJson = CollectionUtils.cloneSet(((JSONObject) json).getInnerMap().keySet());
                                //移除了所有的非业务字段
                                cloneFieldsInJson.removeAll(notBussFields);
                                boolean flag = true;//是否要清除
                                for (String key : cloneFieldsInJson) {
                                    if (StringUtils.isNotEmpty(((JSONObject) json).getString(key))) {
                                        //如果有一个不为"" 就不删除
                                        flag = false;//
                                        break;
                                    }
                                }
                                if (flag) {
                                    jsonArray.remove(json);
                                }
                            }
                        });
                    }
                }
            });
        }
        return root;

    }

    /**
     * 用于设置ifvo的数据
     *
     * @return
     */
    public JSONObject washIFVOField(JSONObject root, List<IFVO> ifvos) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
            }
        }
        final JSONObject json0088ObjectClone = json0088Object;
        if (null != json0088ObjectClone) {
            ifvos.forEach(ifvo -> {
                String belongToTable = ifvo.getBelongToTable();
                if (json0088ObjectClone.containsKey(belongToTable)) {
                    JSONObject jsonObject = json0088ObjectClone.getJSONObject(belongToTable);
                    if (jsonObject.containsKey("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                        JSONArray clondeJsonArray = JSONArray.parseArray(jsonArray.toJSONString());
                        jsonArray.forEach(json -> {
                            if (json instanceof JSONObject) {
                                boolean flag = true;//是否需要设置值
                                for (Map.Entry<String, String> entry : ifvo.getIF().entrySet()) {
                                    String k = entry.getKey();
                                    String v = entry.getValue();
                                    if (null != ((JSONObject) json).getString(k)) {
                                        if (!((JSONObject) json).getString(k).endsWith(v)) {
                                            flag = false;//一个不等就放弃
                                        }
                                    }
                                }
                                if (flag) {
                                    ifvo.getSet().forEach(((JSONObject) json)::put);

                                }

                            }
                        });

                    }
                }
            });
        }
        return root;

    }

    /**
     * 清洗指定字段的<br>为\n
     *
     * @param root
     * @param dealField
     * @return
     */
    public JSONObject dealHtmlAndBr(JSONObject root, List<String> dealField) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                dealField.forEach(filed -> {
                    if (filed.equalsIgnoreCase(key)) {
                        try {
                            String tmp = "^^^^^^";
                            String cleanValue = ((String) value).replaceAll("<br[\\s]*?/>", tmp);
                            cleanValue = Jsoup.parse(cleanValue).text().replace(tmp, "\n");
                            root.put(key, cleanValue);
                        } catch (Exception e) {
                            LOGGER.info("<br>替换异常:{}", e.toString(), e);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.dealHtmlAndBr((JSONObject) value, dealField);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.dealHtmlAndBr((JSONObject) jsonObjet, dealField);
                });
            }
        }
        return root;
    }

    /**
     * 替换指定 field的 map
     *
     * @param root
     * @param
     * @return
     */
    public JSONObject mapReplace(JSONObject root, Map<String, Map<String, String>> map) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                map.forEach((filed, valueMap) -> {
                    if (filed.equalsIgnoreCase(key)) {
                        //如果字段名称相等
                        if (StringUtils.isNotEmpty(valueMap.get(((String) value).toUpperCase()))) {
                            root.put(key, valueMap.get(value));
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.mapReplace((JSONObject) value, map);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.mapReplace((JSONObject) jsonObjet, map);
                });
            }
        }
        return root;
    }


    /**
     * 注意， 目前写死在F25_0088上面
     * 匹配逻辑
     * 1.州名匹配F6_0099，调取F1_0099前八位，以该八位代码开头、且F4_0099=6的记录为第二步匹配的范围。
     * 2.取Entity Address第二行第一个单词，去1中结果进行匹配，匹配F7_0099(不区分大小写)，若匹配到，调取F1_0099前N位"
     *
     * @param first        州名
     * @param second       城市名
     * @param N            去前多少位数
     * @param defaultValue 返回的默认值
     * @param startValue   指定前缀
     * @return
     */
    private String getCityCode(String first, String second, Integer N, String defaultValue, String startValue) {
        List<TB_OBJECT_0099> list0099 = DataPool.getDataFrom0099List();
        String codeState = "";
        //匹配出州的代码
        for (TB_OBJECT_0099 tb_OBJECT_0099 : list0099) {
            if (first.equals(tb_OBJECT_0099.getF6_0099())
                    && StringUtils.isNotEmpty(tb_OBJECT_0099.getF1_0099())
                    && tb_OBJECT_0099.getF1_0099().startsWith(startValue)) {
                codeState = tb_OBJECT_0099.getF1_0099();
                break;
            }
        }
        //匹配市的代码
        if (StringUtils.isNotEmpty(codeState)) {
            String codePre = codeState.trim().substring(0, 8);
            for (TB_OBJECT_0099 tb_OBJECT_0099 : list0099) {
                if (StringUtils.isNotEmpty(tb_OBJECT_0099.getF1_0099()) && StringUtils.isNotEmpty(tb_OBJECT_0099.getF7_0099())) {
                    String codeF1 = tb_OBJECT_0099.getF1_0099().substring(0, 8);
                    String cityF7 = tb_OBJECT_0099.getF7_0099();
                    //市的代码前8为和州的代码相同 且 市的名称与其相同
                    if (codePre.equalsIgnoreCase(codeF1)
                            && second.equalsIgnoreCase(cityF7)
                            && "6".equalsIgnoreCase(tb_OBJECT_0099.getF4_0099())) {
                        String code = tb_OBJECT_0099.getF1_0099().substring(0, N);
                        if (StringUtils.isNotEmpty(code)) {
                            return code;
                        } else {
                            LOGGER.info("");
                        }
                    }
                }
            }
            //如果匹配不到市代码 就返回州代码
            if (StringUtils.isNotEmpty(codeState.substring(0, N))) {
                return codeState.substring(0, N);
            } else {
                LOGGER.info("");
            }
        }
        LOGGER.error("映射异常 F23_0088 : first:{} second:{} N:{}  defaultValue:{}", first, second, N, defaultValue);
        return defaultValue;

    }

    /**
     * 注意， 目前写死在F25_0088上面
     * 匹配逻辑
     * 1.州名匹配F6_0099，调取F1_0099前八位，以该八位代码开头、且F4_0099=6的记录为第二步匹配的范围。
     * 2.取Entity Address第二行第一个单词，去1中结果进行匹配，匹配F7_0099(不区分大小写)，若匹配到，调取F1_0099前N位"
     *
     * @param first        州名
     * @param second       城市名
     * @param N            去前多少位数
     * @param defaultValue 返回的默认值
     * @return
     */
    private String getCityCode(String first, String second, Integer N, String defaultValue) {
        List<TB_OBJECT_0099> list0099 = DataPool.getDataFrom0099List();
        String codeState = "";
        //匹配出州的代码
        for (TB_OBJECT_0099 tb_OBJECT_0099 : list0099) {
            if (first.equals(tb_OBJECT_0099.getF6_0099())) {
                codeState = tb_OBJECT_0099.getF1_0099();
                break;
            }
        }
        //匹配市的代码
        if (StringUtils.isNotEmpty(codeState)) {
            String codePre = codeState.trim().substring(0, 8);
            for (TB_OBJECT_0099 tb_OBJECT_0099 : list0099) {
                if (StringUtils.isNotEmpty(tb_OBJECT_0099.getF1_0099()) && StringUtils.isNotEmpty(tb_OBJECT_0099.getF7_0099())) {
                    String codeF1 = tb_OBJECT_0099.getF1_0099().substring(0, 8);
                    String cityF7 = tb_OBJECT_0099.getF7_0099();
                    //市的代码前8为和州的代码相同 且 市的名称与其相同
                    if (codePre.equalsIgnoreCase(codeF1)
                            && second.equalsIgnoreCase(cityF7)
                            && "6".equalsIgnoreCase(tb_OBJECT_0099.getF4_0099())) {
                        String code = tb_OBJECT_0099.getF1_0099().substring(0, N);
                        if (StringUtils.isNotEmpty(code)) {
                            return code;
                        } else {
                            LOGGER.info("");
                        }
                    }
                }
            }
            //如果匹配不到市代码 就返回州代码
            if (StringUtils.isNotEmpty(codeState.substring(0, N))) {
                return codeState.substring(0, N);
            } else {
                LOGGER.info("");
            }
        }
        LOGGER.error("映射异常 F23_0088 : first:{} second:{} N:{}  defaultValue:{}", first, second, N, defaultValue);
        return defaultValue;

    }

    /**
     * 清洗排除在外的字段 字段直接赋值为""
     *
     * @return
     */
    public JSONObject washExcludeFiledValue(JSONObject root, List<WashExcludeFiled> washExcludeFileds) {

        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                washExcludeFileds.forEach(washExcludeFiled -> {
                    if (washExcludeFiled.getFieldName().equalsIgnoreCase(key)) {
                        try {
                            if (washExcludeFiled.getExcludeValues().contains(value.toString())) {
                                root.put(key, "");
                            }
                        } catch (Exception e) {
                            LOGGER.info(" 清洗排除在外的字段:{},washExcludeFiled:{}", value, washExcludeFiled);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washExcludeFiledValue((JSONObject) value, washExcludeFileds);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washExcludeFiledValue((JSONObject) jsonObjet, washExcludeFileds);
                });
            }
        }
        return root;

    }

    /**
     * 字段值枚举 落盘文件
     *
     * @return
     */
    public JSONObject washExcludeFiledValueToLocation(JSONObject
                                                              root, List<WashExcludeFiled> washExcludeFileds, WarnInfos warnInfo) {

        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                washExcludeFileds.forEach(washExcludeFiled -> {
                    if (washExcludeFiled.getFieldName().equalsIgnoreCase(key)) {
                        if (washExcludeFiled.getExcludeValues().contains(value.toString())) {
                            warnDealService.addSeriousWarnInfo(warnInfo, "无需处理的落盘", "异常", "");
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washExcludeFiledValueToLocation((JSONObject) value, washExcludeFileds, warnInfo);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washExcludeFiledValueToLocation((JSONObject) jsonObjet, washExcludeFileds, warnInfo);
                });
            }
        }
        return root;

    }


    /**
     * 默认下的 root 下的 @attachmentConfig
     * info.txt 下的 downLoadFileUrls(可以指定名称)
     *
     * @param root
     * @return
     */
    public List<AttachmentVo> getAttachmentVos(JSONObject root, String downLoadFileUrls) {
        List<AttachmentVo> attachmentVos = new ArrayList<>();
        if (root.containsKey("@attachmentConfig")) {
            JSONArray attachmentConfigArray = root.getJSONArray("@attachmentConfig");
            if (null != attachmentConfigArray && attachmentConfigArray.size() > 0) {
                attachmentConfigArray.forEach(json -> {
                    if (json instanceof JSONObject) {
                        AttachmentVo attachmentVo = JSONObject.toJavaObject((JSONObject) json, AttachmentVo.class);
                        //存储上传的附件信息
                        attachmentVos.add(attachmentVo);
                    }
                });
            }
        }
        Map<String, AttachmentVo> map = new HashMap<>();
        attachmentVos.forEach(vo -> {
            map.put(vo.getFileName(), vo);
        });
        if (root.containsKey("info.txt")) {
            JSONObject infoJsonObject = root.getJSONObject("info.txt");
            if (null != infoJsonObject) {
                if (infoJsonObject.containsKey(downLoadFileUrls)) {
                    JSONObject downLoadFileUrlsJsonObject = infoJsonObject.getJSONObject(downLoadFileUrls);
                    if (null != downLoadFileUrlsJsonObject && downLoadFileUrlsJsonObject.size() > 0) {
                        downLoadFileUrlsJsonObject.forEach((key, value) -> {
                            if (value instanceof String) {
                                if (null != map.get(key)) {
                                    //如果查询出了对应的附件信息
                                    map.get(key).setDownloadUrl(((String) value).trim());
                                }
                            }
                        });

                    }
                }
            }
        }
        //把附件信息提取出来
        List<AttachmentVo> result = new ArrayList<>();
        map.forEach((key, value) -> {
            result.add(value);
        });
        return result;
    }

    /**
     * 默认下的 root 下的 @attachmentConfig
     * 简化
     *
     * @param root
     * @return
     */
    public List<AttachmentVo> getAttachmentVos(JSONObject root) {
        List<AttachmentVo> attachmentVos = new ArrayList<>();
        if (root.containsKey("@attachmentConfig")) {
            JSONArray attachmentConfigArray = root.getJSONArray("@attachmentConfig");
            if (null != attachmentConfigArray && attachmentConfigArray.size() > 0) {
                attachmentConfigArray.forEach(json -> {
                    if (json instanceof JSONObject) {
                        AttachmentVo attachmentVo = JSONObject.toJavaObject((JSONObject) json, AttachmentVo.class);
                        //存储上传的附件信息
                        attachmentVos.add(attachmentVo);
                    }
                });
            }
        }
        return attachmentVos;
    }

    /**
     * 清洗指定字段的html转为字符串
     *
     * @param root
     * @param dealField
     * @return
     */
    public JSONObject washHtmlToChar(JSONObject root, List<String> dealField) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                dealField.forEach(filed -> {
                    if (filed.equalsIgnoreCase(key)) {
                        try {
                            String cleanValue = StringEscapeUtils.unescapeHtml(((String) value).trim());
                            root.put(key, cleanValue);
                        } catch (Exception e) {
                            LOGGER.info("<br>替换异常:{}", e.toString(), e);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washHtmlToChar((JSONObject) value, dealField);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washHtmlToChar((JSONObject) jsonObjet, dealField);
                });
            } else {
                continue;
            }
        }
        return root;
    }

    /**
     * 清洗指定字段的 多个字符串合一
     * 注意会去掉\n！！！
     *
     * @param root
     * @param dealField
     * @return
     */
    public JSONObject washMoreBlankToOne(JSONObject root, List<String> dealField) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                dealField.forEach(filed -> {
                    if (filed.equalsIgnoreCase(key)) {
                        try {
                            String cleanValue = ((String) value).trim().replaceAll("[\\s ]+", " ");
                            root.put(key, cleanValue);
                        } catch (Exception e) {
                            LOGGER.info("多个字符串合一  异常:{}", e.toString(), e);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washMoreBlankToOne((JSONObject) value, dealField);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washMoreBlankToOne((JSONObject) jsonObjet, dealField);
                });
            }
        }
        return root;
    }

    /**
     * 清洗指定字段的 \n+" "字符合并为\n
     * <p>
     * -> 用于处理第二行换行之后又空格问题
     *
     * @param root
     * @param dealField
     * @return
     */
    public JSONObject washEnterMoreBlankToEnter(JSONObject root, List<String> dealField) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                dealField.forEach(filed -> {
                    if (filed.equalsIgnoreCase(key)) {
                        try {
                            String cleanValue = ((String) value).trim().replaceAll("[ ]*\n[ ]*", "\n");
                            root.put(key, cleanValue);
                        } catch (Exception e) {
                            LOGGER.info("多个字符串合一  异常:{}", e.toString(), e);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washEnterMoreBlankToEnter((JSONObject) value, dealField);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washEnterMoreBlankToEnter((JSONObject) jsonObjet, dealField);
                });
            }
        }
        return root;
    }

    /**
     * 清洗指定字段的 多个字符串合一
     *
     * @param root
     * @param dealField
     * @return
     */
    public JSONObject washOnlyMoreBlankToOne(JSONObject root, List<String> dealField) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                dealField.forEach(filed -> {
                    if (filed.equalsIgnoreCase(key)) {
                        try {
                            String cleanValue = ((String) value).trim().replaceAll("[ ]+", " ");
                            root.put(key, cleanValue);
                        } catch (Exception e) {
                            LOGGER.info("多个字符串合一  异常:{}", e.toString(), e);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washOnlyMoreBlankToOne((JSONObject) value, dealField);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washOnlyMoreBlankToOne((JSONObject) jsonObjet, dealField);
                });
            }
        }
        return root;
    }


    /**
     * 用于清洗重复的数组（这里不包含0088）
     *
     * @return
     */
    public JSONObject washRepeatArray(JSONObject root, List<WashRepeatArrayVo> washRepeatArrayVos) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
            }
        }
        final JSONObject json0088ObjectClone = json0088Object;
        if (null != json0088ObjectClone) {
            washRepeatArrayVos.forEach(washBussFieldvo -> {
                String belongToTable = washBussFieldvo.getBelongToTable();
                Set<String> fields = washBussFieldvo.getFields();
                if (json0088ObjectClone.containsKey(belongToTable)) {
                    JSONObject jsonObject = json0088ObjectClone.getJSONObject(belongToTable);
                    if (jsonObject.containsKey("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONArray jsonArrayResult = this.delRepeatJson(jsonArray, fields);
                        if (!jsonArrayResult.isEmpty()) {
                            jsonObject.put("data", jsonArrayResult);
                        }
                    }
                }
            });
        }
        return root;
    }

    /**
     * 默认去重方法，仅对JSONArray中完全相同的两个元素进行去重操作
     *
     * @param root 需要处理的报文
     */

    public void defaultWashRepeatArray(JSONObject root) {
        JSONObject json0088Object = new JSONObject();
        if (root.containsKey(PREFIX + "_0088")) {
            JSONArray data = root.getJSONObject(PREFIX + "_0088").getJSONArray("data");
            if (data.size() == 1) {
                json0088Object = data.getJSONObject(0);
            }
        }
        if (null != json0088Object) {
            Set<String> items = json0088Object.keySet();
            for (String s : items) {
                if (s.startsWith(PREFIX)) {
                    Object obj = json0088Object.get(s);
                    if (obj instanceof JSONObject) {
                        JSONArray arr = ((JSONObject) obj).getJSONArray("data");
                        Set<JSONObject> tmp = new HashSet<>();
                        for (int i = 0; i < arr.size(); i++) {
                            tmp.add(arr.getJSONObject(i));
                        }
                        obj = JSONArray.parseArray(tmp.toString());
                        JSONObject data = new JSONObject();
                        data.put("data", obj);
                        json0088Object.put(s, data);
                    }
                }
            }
        }
    }


    /**
     * 用于移除 当特定字段为特定值时 ， 去除整条数据（这里不包含0088）
     * <p>
     * 如果业务字段全部为0 ， 就移除
     *
     * @return
     */
    public JSONObject washKeyValueArray(JSONObject root, List<WashKeyValueArrayVo> washKeyValueArrayVos) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
            }
        }
        final JSONObject json0088ObjectClone = json0088Object;
        if (null != json0088ObjectClone) {

            for (WashKeyValueArrayVo keyValueArrayVo : washKeyValueArrayVos) {
                String belongToTable = keyValueArrayVo.getBelongToTable();
                List<WashKeyValueArrayVo.KeyValue> keyValues =
                        keyValueArrayVo.getKeyValues();

                if (json0088ObjectClone.containsKey(belongToTable)) {
                    JSONObject jsonObject = json0088ObjectClone.getJSONObject(belongToTable);
                    if (jsonObject.containsKey("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONArray clondeJsonArray = JSONArray.parseArray(jsonArray.toJSONString());
                        //移除了所有的非业务字段
                        for (Object json : clondeJsonArray) {
                            if (json instanceof JSONObject) {
                                boolean[] flags = new boolean[keyValues.size()];//记录每个字段是否相等
                                for (int i = 0, keyValuesSize = keyValues.size(); i < keyValuesSize; i++) {
                                    WashKeyValueArrayVo.KeyValue keyValue = keyValues.get(i);
                                    String field = keyValue.getField();//指定key
                                    if (((JSONObject) json).containsKey(field)) {
                                        String value = ((JSONObject) json).getString(field);
                                        for (String s : keyValue.getValues()) { //指定value
                                            if (s.equals(value)) {
                                                flags[i] = true;//如果value 和指定的值有一个相等， 就删除
                                                break;
                                            }
                                        }

                                    }
                                }
                                boolean result = true;
                                for (boolean flag : flags) {
                                    if (!flag) {
                                        result = false;
                                    }
                                }
                                if (result) {
                                    jsonArray.remove(json);
                                }

                            }
                        }
                    }
                }
            }
        }
        return root;

    }

    /**
     * 用于移除 当特定字段 == 特定值时 ， 去除整条数据（这里不包含0088）
     * <p>
     * 如果业务字段全部为0 ， 就移除
     * flag,是否忽略大小写
     *
     * @return
     */
    public JSONObject washKeyEqualValueArray(JSONObject root, List<WashKeyValueArrayVo> washKeyValueArrayVos, boolean flag) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
            }
        }
        final JSONObject json0088ObjectClone = json0088Object;
        if (null != json0088ObjectClone) {

            for (WashKeyValueArrayVo keyValueArrayVo : washKeyValueArrayVos) {
                String belongToTable = keyValueArrayVo.getBelongToTable();
                List<WashKeyValueArrayVo.KeyValue> keyValues =
                        keyValueArrayVo.getKeyValues();

                if (json0088ObjectClone.containsKey(belongToTable)) {
                    JSONObject jsonObject = json0088ObjectClone.getJSONObject(belongToTable);
                    if (jsonObject.containsKey("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONArray clondeJsonArray = JSONArray.parseArray(jsonArray.toJSONString());
                        //移除了所有的非业务字段
                        for (Object json : clondeJsonArray) {
                            if (json instanceof JSONObject) {
                                boolean[] flags = new boolean[keyValues.size()];//记录每个字段是否相等
                                for (int i = 0, keyValuesSize = keyValues.size(); i < keyValuesSize; i++) {
                                    WashKeyValueArrayVo.KeyValue keyValue = keyValues.get(i);
                                    String field = keyValue.getField();//指定key
                                    if (((JSONObject) json).containsKey(field)) {
                                        String value = ((JSONObject) json).getString(field);
                                        for (String s : keyValue.getValues()) { //指定value
                                            if (flag) {
                                                //如果忽略大小写
                                                s = s.toUpperCase();
                                                value = value.toUpperCase();
                                            }
                                            LOGGER.info("value:{}", value);
                                            if (value.equals(s)) {
                                                flags[i] = true;//如果value 和指定的值有一个相等， 就删除
                                                LOGGER.info("value - remove:{}", value);
                                                break;
                                            }
                                        }

                                    }
                                }
                                boolean result = true;
                                for (boolean b : flags) {
                                    if (!b) {
                                        result = false;
                                    }
                                }
                                if (result) {
                                    jsonArray.remove(json);
                                }

                            }
                        }
                    }
                }
            }
        }
        return root;

    }


    /**
     * 用于移除 当特定字段包含特定值时 ， 去除整条数据（这里不包含0088）
     * <p>
     * 如果业务字段全部为0 ， 就移除
     * flag,是否忽略大小写
     *
     * @return
     */
    public JSONObject washKeyContainValueArray(JSONObject root, List<WashKeyValueArrayVo> washKeyValueArrayVos, boolean flag) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
            }
        }
        final JSONObject json0088ObjectClone = json0088Object;
        if (null != json0088ObjectClone) {

            for (WashKeyValueArrayVo keyValueArrayVo : washKeyValueArrayVos) {
                String belongToTable = keyValueArrayVo.getBelongToTable();
                List<WashKeyValueArrayVo.KeyValue> keyValues =
                        keyValueArrayVo.getKeyValues();

                if (json0088ObjectClone.containsKey(belongToTable)) {
                    JSONObject jsonObject = json0088ObjectClone.getJSONObject(belongToTable);
                    if (jsonObject.containsKey("data")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONArray clondeJsonArray = JSONArray.parseArray(jsonArray.toJSONString());
                        //移除了所有的非业务字段
                        for (Object json : clondeJsonArray) {
                            if (json instanceof JSONObject) {
                                boolean[] flags = new boolean[keyValues.size()];//记录每个字段是否相等
                                for (int i = 0, keyValuesSize = keyValues.size(); i < keyValuesSize; i++) {
                                    WashKeyValueArrayVo.KeyValue keyValue = keyValues.get(i);
                                    String field = keyValue.getField();//指定key
                                    if (((JSONObject) json).containsKey(field)) {
                                        String value = ((JSONObject) json).getString(field);
                                        for (String s : keyValue.getValues()) { //指定value
                                            if (flag) {
                                                //如果忽略大小写
                                                s = s.toUpperCase();
                                                value = value.toUpperCase();
                                            }
                                            LOGGER.info("value:{}", value);
                                            if (value.contains(s)) {
                                                flags[i] = true;//如果value 和指定的值有一个相等， 就删除
                                                LOGGER.info("value - remove:{}", value);
                                                break;
                                            }
                                        }

                                    }
                                }
                                boolean result = true;
                                for (boolean b : flags) {
                                    if (!b) {
                                        result = false;
                                    }
                                }
                                if (result) {
                                    jsonArray.remove(json);
                                }

                            }
                        }
                    }
                }
            }
        }
        return root;

    }

    /**
     * 所有字段 - 去除前后空格
     *
     * @param root
     * @return
     */
    public JSONObject washAllStrTrim(JSONObject root) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                //如果是str , 就去去除前后空格
                String cleanValue = ((String) value).trim();
                root.put(key, cleanValue);
            } else if (value instanceof JSONObject) {
                this.washAllStrTrim((JSONObject) value);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washAllStrTrim((JSONObject) jsonObjet);
                });
            }
        }
        return root;
    }

    /**
     * @param source        源字符串
     * @param regExpression 正则表达式
     * @param index         取匹配的index
     * @return
     */
    public static List<String> RegexValueList(String source, String regExpression, Integer index) {
        List<String> rets = new ArrayList();
        Pattern p = Pattern.compile(regExpression);
        Matcher m = p.matcher(source);

        while (m.find()) {
            rets.add(m.group(index));
        }
        return rets;
    }

    /**
     * 返回匹配到的第一个 -> 没有就算返回""
     *
     * @param source        源字符串
     * @param regExpression 正则表达式
     * @param index         取匹配的index
     * @return
     */
    public static String RegexValueListFirst(String source, String regExpression, Integer index) {
        List<String> rets = WashService.RegexValueList(source, regExpression, index);
        if (rets.size() > 0) {
            return rets.get(0);
        } else {
            return "";
        }
    }


    /**
     * 去除重复的json
     *
     * @param array
     */
    private JSONArray delRepeatJson(JSONArray array, Set<String> fields) {
        JSONArray arrayTempi = new JSONArray();
        JSONArray arrayTempj = new JSONArray();
        int len = array.size();
        int i, j;
        for (i = 0; i <= len - 1; i++)          //外层循环控制趟数，总趟数为len-1
            for (j = i + 1; j <= len - 1; j++) {  //内层循环为当前i趟数 所需要比较的次数
                JSONObject ijson = array.getJSONObject(i);
                JSONObject jjson = array.getJSONObject(j);
                if (diffJson(ijson, jjson, fields)) {
                    if (!arrayTempi.contains(jjson)) {
                        arrayTempi.add(jjson);
                        arrayTempj.add(jjson);
                    }
                    break;
                }

            }
        array.removeAll(arrayTempi);
        array.removeAll(arrayTempj);
        array.addAll(arrayTempi);
        return array;
    }

    /**
     * true 相等
     *
     * @param source
     * @param target
     * @param fields
     * @return
     */
    private boolean diffJson(JSONObject source, JSONObject target, Set<String> fields) {
        boolean flag = true;
        for (String field : fields) {
            if (null != source && null != target) {
                if (null != source.get(field) && null != target.get(field)) {
                    if (!source.get(field).equals(target.get(field))) {
                        //如果有一次不等就是不等
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 获取指定字段值
     *
     * @param root
     * @param field
     * @return
     */
    public String getFieldValue0088(JSONObject root, String field) {
        String value = "";
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
                if (null != json0088Object) {
                    if (json0088Object.containsKey(field)) {
                        value = json0088Object.getString(field);
                    }
                }
            }

        }
        return value;
    }

    /**
     * 0088 指定值
     *
     * @param root
     * @param field
     * @return
     */
    public String putFieldValue0088(JSONObject root, String field, String value) {
        JSONObject json0088Object = null;
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                json0088Object = json0088OArray.getJSONObject(0);
                if (null != json0088Object) {
                    json0088Object.put(field, value);
                }
            }

        }
        return value;
    }

    /**
     * 函数式处理字段 本身
     *
     * @param root
     * @return
     */
    public JSONObject washFunctionDealField(JSONObject root, Function<String, String> function, String field) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {

                if (field.equalsIgnoreCase(key) && StringUtils.isNotEmpty(value.toString())) {
                    try {
                        String cleanValue = function.apply(value.toString());
                        if (null != cleanValue) {
                            root.put(key, cleanValue);
                        }
                    } catch (Exception e) {
                        LOGGER.info("多个字符串合一  异常:{}", e.toString(), e);
                    }

                }
            } else if (value instanceof JSONObject) {
                this.washFunctionDealField((JSONObject) value, function, field);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washFunctionDealField((JSONObject) jsonObjet, function, field);
                });
            }
        }
        return root;
    }

    /**
     * 函数式处理字段 本身
     *
     * @param root
     * @param function
     * @param fields   -> 同时处理多个字段
     * @return
     */
    public JSONObject washFunctionDealFields(JSONObject root, Set<String> fields, Function<String, String> function) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                fields.forEach(field -> {
                    if (field.equalsIgnoreCase(key) && StringUtils.isNotEmpty(value.toString())) {
                        try {
                            String cleanValue = function.apply(value.toString());
                            if (null != cleanValue) {
                                root.put(key, cleanValue);
                            }
                        } catch (Exception e) {
                            LOGGER.info("多个字符串合一  异常:{}", e.toString(), e);
                        }

                    }
                });
            } else if (value instanceof JSONObject) {
                this.washFunctionDealFields((JSONObject) value, fields, function);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washFunctionDealFields((JSONObject) jsonObjet, fields, function);
                });
            }
        }
        return root;
    }


    /**
     * 用于获取正则数组
     *
     * @param source
     * @param washValue
     * @param excludeStrs 不转成json字符串的字符串
     * @return
     */
    public static String RegexValueList(String source, String washValue, List<String> excludeStrs) {
        List<String> valueList = new ArrayList<>();
        List<String> valueResult = new ArrayList<>();
        JSONObject confJson = JSONObject.parseObject(washValue);
        for (String regex : confJson.keySet()) {
            Integer index = confJson.getInteger(regex);
            valueList = cn.com.wind.util.StringUtils.getStrsByRegular(source, regex, index);
            valueList.forEach(value -> {
                if (!excludeStrs.contains(value)) {
                    valueResult.add(value);
                }
            });
        }
        return JSONArray.toJSONString(valueResult);
    }

    /**
     * 用于获取正则数组
     *
     * @param source
     * @param washValue
     * @return
     */
    public static List<String> RegexValueList(String source, String washValue) {
        List<String> valueList = new ArrayList<>();
        JSONObject confJson = JSONObject.parseObject(washValue);
        for (String regex : confJson.keySet()) {
            Integer index = confJson.getInteger(regex);
            valueList = cn.com.wind.util.StringUtils.getStrsByRegular(source, regex, index);
        }
        return valueList;
    }


    /**
     * 用于获取 jsonArrary 数组本身的引用
     * <p>
     *
     * @return
     */
    public JSONArray getTableArrayByTableName(JSONObject root, String tableName) {
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                JSONObject json0088Object = json0088OArray.getJSONObject(0);
                if (!json0088Object.isEmpty()) {
                    if (json0088Object.containsKey(tableName)) {
                        Object o = json0088Object.get(tableName);
                        if (o instanceof JSONObject) {
                            if (((JSONObject) o).containsKey("data")) {
                                Object dataTable = ((JSONObject) o).get("data");
                                if (dataTable instanceof JSONArray) {
                                    return ((JSONObject) o).getJSONArray("data");
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 把 jsonArrary 数组本身 放入 root
     * <p>
     *
     * @return
     */
    public JSONObject putTableArrayByTableName(JSONObject root, JSONArray tableJsonArray, String tableName) {
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                JSONObject json0088Object = json0088OArray.getJSONObject(0);
                JSONObject dataTable = new JSONObject();
                dataTable.put("data", tableJsonArray);
                json0088Object.put(tableName, dataTable);
            }
        }
        return root;
    }


    /**
     * 把 jsonArrary 数组本身 放入 root
     * <p>
     *
     * @return
     */
    public JSONObject removeTableArrayByTableName(JSONObject root, String tableName) {
        if (root.containsKey(PREFIX + "_0088")) {
            JSONObject data = root.getJSONObject(PREFIX + "_0088");
            JSONArray json0088OArray = data.getJSONArray("data");
            if (json0088OArray.size() == 1) {
                JSONObject json0088Object = json0088OArray.getJSONObject(0);
                json0088Object.remove(tableName);
            }
        }
        return root;
    }


    /**
     * 根据F3_0099 -> 查询出0099的实体
     *
     * @param F3_0099
     * @return
     */
    public TB_OBJECT_0099 getTBOBJECT0099ByF3(String F3_0099) {
        List<TB_OBJECT_0099> list0099 = DataPool.getDataFrom0099List();
        //匹配出州的代码
        for (TB_OBJECT_0099 tb_OBJECT_0099 : list0099) {
            if (F3_0099.equals(tb_OBJECT_0099.getF3_0099())) {
                return tb_OBJECT_0099;
            }
        }
        return null;
    }

    /**
     * 内部使用的递归
     *
     * @param root
     * @param wash0003s
     * @param warnInfo
     * @param flag      true 落盘 flag 不落盘
     * @return
     */
    private JSONObject washF3_0003ByF2_0003Loop(JSONObject root, List<Wash0003> wash0003s, WarnInfos warnInfo,
                                                boolean flag) {
        for (Map.Entry<String, Object> entry : root.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                wash0003s.forEach(wash0003 -> {
                    if (StringUtils.isNotEmpty(wash0003.getFromField())
                            && StringUtils.isNotEmpty(wash0003.getTargetField())
                            && StringUtils.isNotEmpty(wash0003.getBelongToType())) {
                        //检查格式是否正常
                        if (wash0003.getFromField().equalsIgnoreCase(key)) {
                            try {
                                //直接转格式，异常不落盘
                                if (!wash0003.getFromFieldValueNotDeal().contains(((String) value).trim())) {
                                    //不处理指定值
                                    String valueWashed = DataPool.getDataFrom0003(wash0003.getBelongToType(), ((String) value).trim());
                                    if (flag) {
                                        if (StringUtils.isEmpty(valueWashed)) {
                                            warnDealService.addSeriousWarnInfo(warnInfo, "映射异常", wash0003.getTargetField() + "异常 ", "");
                                            LOGGER.error("映射异常 : fromField:{} targetField:{} BelongToType:{} fromFieldValue:{}",
                                                    wash0003.getFromField(),
                                                    wash0003.getTargetField(),
                                                    wash0003.getBelongToType(),
                                                    value);
                                            root.put(wash0003.getTargetField(),
                                                    "映射异常 : fromField:" + wash0003.getFromField()
                                                            + " targetField:" + wash0003.getTargetField()
                                                            + " BelongToType:" + wash0003.getBelongToType()
                                                            + " fromFieldValue:" + value);
                                        } else {
                                            root.put(wash0003.getTargetField(), valueWashed);
                                        }
                                    } else {
                                        if (org.apache.commons.lang3.StringUtils.isNotBlank(valueWashed)) {
                                            root.put(wash0003.getTargetField(), valueWashed);
                                        } else {
                                            LOGGER.error("映射异常(无需处理) : fromField:{} targetField:{} BelongToType:{} fromFieldValue:{}",
                                                    wash0003.getFromField(),
                                                    wash0003.getTargetField(),
                                                    wash0003.getBelongToType(),
                                                    value);
                                            root.put(wash0003.getTargetField(), "");
                                        }
                                    }

                                }
                            } catch (Exception e) {
                                LOGGER.info("清洗0003失败:{},format:{}", value, wash0003s);
                            }
                        }
                    }
                });
            } else if (value instanceof JSONObject) {
                this.washF3_0003ByF2_0003Loop((JSONObject) value, wash0003s, warnInfo, flag);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                jsonArray.forEach(jsonObjet -> {
                    this.washF3_0003ByF2_0003Loop((JSONObject) jsonObjet, wash0003s, warnInfo, flag);
                });
            }
        }
        return root;

    }


    public static String formatDateStr(String date, String fromFormat, String toFormat) throws ParseException {
        Date dateStr = null;
        if (!StringUtils.isEmpty(date) && !StringUtils.isEmpty(fromFormat) && !StringUtils.isEmpty(toFormat)) {
            if (fromFormat.equals(toFormat)) {
                return date;
            } else {
                dateStr = formatDate(date, fromFormat);
                return formatDateStr(dateStr, toFormat);
            }
        } else {
            return date;
        }
    }

    private static Date formatDate(String dateSt, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date dt = null;
        dt = sdf.parse(dateSt);
        return dt;
    }

    private static String formatDateStr(Date date, String dateFormat) {
        if (null == date) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.format(date);
        }
    }
    //  "F4_6178": "00010101"

    /**
     * 计算指定字符串发 target的数量
     */
    public int count(String source, String target) {
        int count = 0;
        while (source.indexOf(target) >= 0) {
            source = source.substring(source.indexOf(target) + target.length());
            count++;
        }
        return count;
    }
}
