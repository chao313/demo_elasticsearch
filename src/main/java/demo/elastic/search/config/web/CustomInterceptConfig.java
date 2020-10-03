package demo.elastic.search.config.web;

import demo.elastic.search.thread.ThreadLocalFeign;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 继承了mvc的适配器 复写其中的addInterceptors方法
 * 请求拦截器（HTTP层面的）
 */
@Configuration
public class CustomInterceptConfig extends WebMvcConfigurerAdapter {

    public static final String HEADER_KEY = "ES_HOST";

    private static Logger LOGGER = LoggerFactory.getLogger(CustomInterceptConfig.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 自定义拦截器
         */
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                LOGGER.info("【拦截请求】: 请求路径 {}", request.getRequestURI());
                String ES_HOST = request.getHeader(HEADER_KEY);
                if (StringUtils.isNotBlank(ES_HOST)) {
                    ThreadLocalFeign.setESHOST(ES_HOST);//放入线程变量
                }
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//                LOGGER.info("【拦截请求】: 请求路径 {}", request.getRequestURI());
            }
        };
        /**
         * 把创建的拦截器注册
         *
         * /**是拦截所有请求
         */
        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");

    }
}
