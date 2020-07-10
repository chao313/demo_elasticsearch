package demo.elastic.search.out.association.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class WindCodeService {
    @Autowired
    private FeignServiceNew feignServiceNew;

    /**
     * 调用外部服务来查询wind id
     * companyName	公司名				        必填
     * fieldName	公司名对应的字段名				必填（大写）
     * tableName	公司名对应的字段所在的表名		必填（大写）
     * source	    来源				            必填
     * filter.type	企业名称类型代码			    可选
     * filter.area	企业区域代码                  可选
     *
     * @param companyName
     * @return 输出参数  :
     * code	代码取值与含义：
     * :1    传入参数错误,类别识别错误请联系刘瑞修改
     * :2    接口调用成功，正确返回结果
     * :3    接口调用成功，但是返回结果为空
     * :4    识别服务错误,联系刘瑞查询原因
     * :5    无调用权限,调用表或字段或来源不在配置表中，联系刘瑞加一下
     * msg:返回错误信息
     * 1.公司名错误
     * 2.传入的filter内参数错误
     * <p>
     * resultArray
     * windCode	    万得号			"area，comstatus为0088查询的信息，type为6254的名称类别,0088、6254表数据都会返回"
     * type     	企业名称类型代码
     * area	        企业区域代码
     * comStatus	企业在业状态
     */
    public Set<String> getWindCodeByCompanyNameNew(String companyName,
                                                    String tableName,
                                                    String fieldName,
                                                    String source,
                                                    String filter) {
        String res = feignServiceNew.getWindCodeByCompanyNameOrCrawlNew(companyName, tableName, fieldName, source, filter);
        JSONObject josn = JSON.parseObject(res);
        Set<String> result = new HashSet<>();
        if (josn == null) {
            log.info("没有返回");
        } else if ("2".equalsIgnoreCase(josn.getString("code"))) {
            JSONArray resultArray = josn.getJSONArray("resultArray");
            if (resultArray.size() != 0) {
                String str = "";
                for (Object object : resultArray) {
                    JSONObject object1 = (JSONObject) object;
                    if (!object1.getString("windcode").startsWith("20")) {
                        /**
                         * 必须不是20开头的
                         */
                        result.add(object1.getString("windcode"));
                    }
                }
            }
        }
        //todo ...(等待逻辑补充)
        return result;
    }
}
