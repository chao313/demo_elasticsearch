package demo.elastic.search.framework.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 2018/8/9    Created by   chao
 */
@Aspect
@Component
public class FeignAspect {
    protected final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 定义切面执行的方法
     */
    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    private void pointCut() {
    }

    /**
     * ProceedingJoinPoint 继承的 JoinPoint 比JoinPoint ， 只多了执行的proceed Around </>一旦执行了这个方法，如果不调用proceed
     * ， 就会导致调用终止 注意：当核心业务抛异常后，立即退出，转向AfterAdvice 执行完AfterAdvice，再转到ThrowingAdvice
     */
    @Around(value = "pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            Object result = joinPoint.proceed(); //继续下一个方法的调用 ：就是调用拦截的函数，得到拦截函数执行的结果
            logger.info("feign请求正常 -> 结果:{}", JSONObject.toJSON(result));
            return result;
        } catch (Exception e) {
            logger.info("feign请求异常 -> 结果:{}", e.toString(), e);
            return e.getMessage();
        }
    }
}
