package demo.elastic.search.out.etl.vo;

import lombok.Data;

@Data
public class OCFullNameMapShortNameAndCode {

    //全称,简写,中文名称,地区代码,地区代码取值位数,登记机关代码,是否走PEVC入库
    private String fullName;//全称
    private String shortName;//简写
    private String chineseName;//中文名称
    private String F23_0088;//地区代码
    private String length;//地区代码取值位数
    private String F26_0088;//登记机关代码
    private String isPevc;//是否走PEVC入库
}
