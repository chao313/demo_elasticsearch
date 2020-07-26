package demo.elastic.search.config;

import com.fasterxml.classmate.TypeResolver;
import demo.elastic.search.po.request.dsl.term.RegexpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Created by hwc on 2017/1/16.
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {


    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket ESApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("demo.elastic.search.controller"))
                .paths(PathSelectors.any())
                .build()
                .genericModelSubstitutes(DeferredResult.class)//异步http请求
                .forCodeGeneration(true)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("ESApi");

        docket.alternateTypeRules(AlternateTypeRules.newMapRule(String.class, WildcardType.class));
//        docket.alternateTypeRules( //自定义规则，如果遇到DeferredResult，则把泛型类转成json
//                newRule(typeResolver.resolve(LinkedHashMap.class,typeResolver
//                        typeResolver.resolve(JsonNode.class, WildcardType.class)),
//                        typeResolver.resolve(WildcardType.class)));


//        docket.alternateTypeRules(//解决返回对象为Map>时，Swagger页面报错
//                AlternateTypeRules.newRule(
//                        typeResolver.resolve(Map.class, String.class, typeResolver.resolve(List.class, FuzzyRequest.FuzzyParam.class)),
//                        typeResolver.resolve(Map.class, String.class, WildcardType.class), Ordered.HIGHEST_PRECEDENCE)
//        );
        return docket;

    }

    @Bean
    public Docket OutPevcApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("demo.elastic.search.out"))
                .paths(PathSelectors.any())
                .build()
                .genericModelSubstitutes(DeferredResult.class)//异步http请求
                .forCodeGeneration(true)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .groupName("Out")
//                .produces(Sets.newHashSet("application/octet-stream"))
                ;
    }

    /**
     * api描述信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerDemo")
                .version("0.1")//本次发布的版本
                .build();
    }
}
