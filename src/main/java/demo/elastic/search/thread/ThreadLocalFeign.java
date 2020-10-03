package demo.elastic.search.thread;

import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.config.feign.JAXRSContract;
import demo.elastic.search.config.feign.SpringDecoder;
import demo.elastic.search.config.feign.SpringEncoder;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 远程调用本地线程池
 */
@Slf4j
@Component
public class ThreadLocalFeign {


    private static ThreadLocal<String> ESHOSTThreadLocal = new ThreadLocal<>();//存放URL
    /**
     * HOST -> Class -> BEAN
     */
//    private static Map<String, Map<Class, ThreadLocal<Object>>> hostToClassToThreadBeanMap = Collections.synchronizedMap(new HashMap<>());//存放class和ThreadBean的映射
    private static Map<String, Map<Class, Object>> hostToClassToBeanMap = Collections.synchronizedMap(new HashMap<>());//存放class和Bean的映射


    /**
     * 如果本地线线程变量存在,优先从线程变量中获取，否则，从spring中获取
     *
     * @return
     */
    public static <T> T getFeignService(Class<T> requiredType) {
        /**
         * 优先从本地变量获取
         */
        T result = null;
        String ESHOST = ESHOSTThreadLocal.get();
        if (StringUtils.isNotBlank(ESHOST)) {
            Map<Class, Object> classToThreadBeanMap = hostToClassToBeanMap.get(ESHOST);//根据ESHOST获取 class到本地线程变量的值
            if (null != classToThreadBeanMap) {
                Object bean = classToThreadBeanMap.get(requiredType);//根据class获取本地线程变量的值
                if (null != bean) {
                    result = (T) bean;
                }
            }
        }

        if (null != result) {
            return result;
        } else {
            return AwareUtil.applicationContext.getBean(requiredType);
        }

    }

    /**
     * 设置 ESHOST
     */
    public static void setESHOST(String ESHOST) {
        ESHOSTThreadLocal.set(ESHOST);
    }

    /**
     * 获取 ESHOST
     */
    public static String getESHOST() {
        return ESHOSTThreadLocal.get();
    }


    /**
     * 根据url构造Feign的bean
     */
    public static <T> T buildFeignBean(Class<T> clz, String url) {
        Object bean = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(AwareUtil.applicationContext.getBean(SpringEncoder.class))
                .decoder(AwareUtil.applicationContext.getBean(SpringDecoder.class))
//                .errorDecoder(AwareUtil.applicationContext.getBean(ErrorDecoder.class))
                .target(clz, url);
        return (T) bean;
    }

    /**
     * 如果发现新的url 没有初始化，在这里进行初始化 -> 目的是存放bean，避免重复创建
     *
     * @throws ClassNotFoundException
     */
    public static void init() throws ClassNotFoundException {
        Map<String, Object> feignClientBeansWithAnnotation = AwareUtil.applicationContext.getBeansWithAnnotation(FeignClient.class);
        Set<String> keySet = feignClientBeansWithAnnotation.keySet();
        log.info("初始化本地变量：{}", keySet);
        if (StringUtils.isBlank(ESHOSTThreadLocal.get())) {
            //为空不初始化
            return;
        }
        if (hostToClassToBeanMap.containsKey(ESHOSTThreadLocal.get())) {
            //如果包含 ->
        } else {
            //如果不包含 -> 进行初始化工作
            Map<Class, Object> tmpMap = Collections.synchronizedMap(new HashMap<>());//新建class to Bean的map
            for (String key : keySet) {
                Class<?> clz = Class.forName(key);
                Object bean = ThreadLocalFeign.buildFeignBean(clz, ESHOSTThreadLocal.get());
                tmpMap.put(clz, bean);
            }
            hostToClassToBeanMap.put(ESHOSTThreadLocal.get(), tmpMap);

        }

    }


}

