package com.xdd.springrvmp.utils;

import com.alibaba.fastjson.JSONObject;
import com.xdd.springrvmp.rv.model.SysLog;
import com.xdd.springrvmp.rv.model.SysUser;
import com.xdd.springrvmp.rv.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author AixLeft
 * Date 2021/2/1
 */
@Slf4j
@Aspect
@Component
public class RvLogAspect {
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 定义切点 @Pointcut
     * 在注解的位置切入代码
     */
    @Pointcut("@annotation( com.xdd.springrvmp.utils.RvLog)")
    public void logPointCut() {
    }

    /**
     * 切面
     * @param joinpoint
     */
    @After("logPointCut()")
    public void saveRvLog(JoinPoint joinpoint) {
        SysLog sysLog = new SysLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        RvLog rvLog = method.getAnnotation(RvLog.class);
        if (rvLog != null) {
            String value = rvLog.value();
            sysLog.setOperation(value);
        }
        //获取请求类名
        String className = joinpoint.getTarget().getClass().getName();
        //获取请求方法名
        sysLog.setMethod(className + "." + method.getName());

        //获取请求的方法参数名
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
        //获取请求的方法参数值
        Object[] args = joinpoint.getArgs();
        for (int i = 0; i <args.length-1 ; i++) {
            System.out.println(args[i]);
        }
        //将参数名和参数值 键值对存放 并转为json格式存
        HashMap<String,String> values = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length-1; i++) {
                values.put(paramNames[i], (String) args[i]);
            }
            //前端传的userId属性不加进去了
            values.remove("userId");
            jsonObject.putAll(values);
            sysLog.setParams(jsonObject.toJSONString());
        }
        //创建时间
        sysLog.setCreatTime(new Date());
        //通过Shiro获取登陆用户id
        Subject subject = SecurityUtils.getSubject();
        SysUser sysUser = (SysUser) subject.getSession().getAttribute("loginuser");
        String userId = sysUser.getUserId();
        sysLog.setUserId(userId);

        //获取用户ip地址
//        sysLog.setIp(request.getRequestURI());

        //生成UUID
        sysLog.setId(UUIDUtils.getUUId());
        //控制台打印日志信息
        log.info("日志类信息为："+sysLog.toString());
        sysLogService.add(sysLog);
    }

}
