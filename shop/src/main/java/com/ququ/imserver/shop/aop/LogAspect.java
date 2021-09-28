package com.ququ.imserver.shop.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Macro on 2017/3/9.
 */
@Aspect
@Configuration
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

//    @Autowired
//    private PostLogDao postLogDao;

    //定义切点Pointcut
    @Pointcut("execution(* com.ququ.imserver.shop.controller.*.*(..))")
    public void excudeService(){

    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Map<String,String> parameterMap = new HashMap<>();
        for (String paramkey: request.getParameterMap().keySet()){
            parameterMap.put(paramkey,request.getParameter(paramkey));
        }
        logger.debug("接口请求开始, 请求地址uri: {}, 请求参数: {}", uri, JSON.toJSONString(parameterMap));
        //记录到MongoDb日志
//        SybAdminRequestLog sybAdminRequestLog = new SybAdminRequestLog();
//        Date begin = new Date();
//        sybAdminRequestLog.setTime(simpleDateFormat.format(begin));
//        sybAdminRequestLog.setController(uri);
//        sybAdminRequestLog.setRequestparam(parameterMap);
//        sybAdminRequestLog = postLogDao.save(sybAdminRequestLog,"SybAdminRequestLog");
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
//        Date end = new Date();
//        Integer ttl = (int) (end.getTime()-begin.getTime());
//        sybAdminRequestLog.setResponsetime(simpleDateFormat.format(end));
//        sybAdminRequestLog.setTtl(ttl);
//        if (result instanceof String)
//            sybAdminRequestLog.setResponseparam(JSON.parseObject(result.toString(),ResultJson.class));
//        else
//            sybAdminRequestLog.setResponseparam((ResultJson) result);
//        postLogDao.save(sybAdminRequestLog,"SybAdminRequestLog");
        logger.debug("接口请求结束，controller的返回值是{} ", result instanceof String?result : JSON.toJSONString(result));
        return result;
    }
}
