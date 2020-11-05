package demo.elastic.search.thread;

import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.JAXRSContract;
import demo.elastic.search.config.feign.SpringDecoder;
import demo.elastic.search.config.feign.SpringEncoder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
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
    private static ThreadLocal<Boolean> ESPageThreadLocal = new ThreadLocal<>();//存放是否分页
    private static ThreadLocal<Integer> ESPageSizeThreadLocal = new ThreadLocal<>();//存放分页的每页数量
    private static ThreadLocal<String> ESFilterThreadLocal = new ThreadLocal<>();//存放过滤条件
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
    public static void setES_HOST(String ES_HOST) {
        ESHOSTThreadLocal.set(ES_HOST);
    }

    /**
     * 获取 ESHOST
     */
    public static String getES_HOST() {
        return ESHOSTThreadLocal.get();
    }

    /**
     * 设置 ESPAGE
     */
    public static void setES_PAGE(Boolean ES_PAGE) {
        ESPageThreadLocal.set(ES_PAGE);
    }

    /**
     * 获取 ESPAGE
     */
    public static Boolean getES_PAGE() {
        return ESPageThreadLocal.get();
    }

    /**
     * 设置 ES_PAGE_SIZE
     */
    public static void setES_PAGE_SIZE(Integer ES_PAGE_SIZE) {
        ESPageSizeThreadLocal.set(ES_PAGE_SIZE);
    }

    /**
     * 获取 ES_PAGE_SIZE
     */
    public static Integer getES_PAGE_SIZE() {
        return ESPageSizeThreadLocal.get();
    }

    /**
     * 设置 ES_FILTER
     */
    public static void setES_FILTER(String ES_FILTER) {
        ESFilterThreadLocal.set(ES_FILTER);
    }

    /**
     * 获取 ES_FILTER
     */
    public static String geES_FILTER() {
        return ESFilterThreadLocal.get();
    }

    /**
     * 根据url构造Feign的bean
     */
    public static <T> T buildFeignBean(Class<T> clz, String url) {
        Bootstrap bootstrap = Bootstrap.getBootstrapByUrl(url);
        Bootstrap.KbnVersion kbn_version = bootstrap == null ? Bootstrap.KbnVersion.DEFAULT : bootstrap.getKbn_version();//如果为空 就是默认
        Object bean = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(AwareUtil.applicationContext.getBean(SpringEncoder.class))
                .decoder(AwareUtil.applicationContext.getBean(SpringDecoder.class))
                .errorDecoder(AwareUtil.applicationContext.getBean(ErrorDecoder.class))
                .requestInterceptor((RequestInterceptor) AwareUtil.applicationContext.getBean(kbn_version.getVersion()))//添加拦截器
                .decode404()
                .target(clz, url);
        return (T) bean;
    }

    /**
     * 如果发现新的url 没有初始化，在这里进行初始化 -> 目的是存放bean，避免重复创建
     *
     * @throws ClassNotFoundException
     */
    public static void init() throws ClassNotFoundException {
        if (StringUtils.isBlank(ESHOSTThreadLocal.get())) {
            //为空不初始化
            return;
        }
        if (hostToClassToBeanMap.containsKey(ESHOSTThreadLocal.get())) {
            //如果包含 -> 不做处理
            log.info("已经存在,不做处理");
        } else {
            //如果不包含 -> 进行初始化工作
            log.info("如果不包含 -> 进行初始化工作");
            Map<String, Object> feignClientBeansWithAnnotation = AwareUtil.applicationContext.getBeansWithAnnotation(FeignClient.class);
            Set<String> keySet = feignClientBeansWithAnnotation.keySet();
            log.info("初始化本地变量：{}", keySet);
            Map<Class, Object> tmpMap = Collections.synchronizedMap(new HashMap<>());//新建class to Bean的map
            for (String key : keySet) {
                Class<?> clz = Class.forName(key);
                Object bean = ThreadLocalFeign.buildFeignBean(clz, ESHOSTThreadLocal.get());
                tmpMap.put(clz, bean);
            }
            hostToClassToBeanMap.put(ESHOSTThreadLocal.get(), tmpMap);

        }

    }

    /**
     * 初始化指定默认的地址
     *
     * @param host
     * @throws ClassNotFoundException
     */
    public static void init(String host) throws ClassNotFoundException {
        if (hostToClassToBeanMap.containsKey(host)) {
            //如果包含 -> 不做处理
            log.info("包含,不做处理");
        } else {
            //如果不包含 -> 进行初始化工作
            log.info("不包含 -> 进行初始化工作");
            Map<String, Object> feignClientBeansWithAnnotation = AwareUtil.applicationContext.getBeansWithAnnotation(FeignClient.class);
            Set<String> keySet = feignClientBeansWithAnnotation.keySet();
            log.info("初始化本地变量：{}", keySet);
            Map<Class, Object> tmpMap = Collections.synchronizedMap(new HashMap<>());//新建class to Bean的map
            for (String key : keySet) {
                Class<?> clz = Class.forName(key);
                Object bean = ThreadLocalFeign.buildFeignBean(clz, host);
                tmpMap.put(clz, bean);
            }
            hostToClassToBeanMap.put(host, tmpMap);

        }

    }


}

