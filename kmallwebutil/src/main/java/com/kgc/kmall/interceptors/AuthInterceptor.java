package com.kgc.kmall.interceptors;

import com.kgc.kmall.annotations.LoginRequired;
import com.kgc.kmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否是HandlerMethod，因为访问静态资源时handler是ResourceHttpRequestHandler
//        if(handler.getClass().equals(org.springframework.web.method.HandlerMethod.class)){
//            //获取注解信息
//            HandlerMethod hm=(HandlerMethod)handler;
//            LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);
//            if(methodAnnotation==null){
//                //定义一个token
//                String token="";
//                //从Cookie中取
//                String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);
//                if(StringUtils.isNotBlank(oldToken)){
//
//                    token=oldToken;
//                }
//                //取token
//                String newToken = request.getParameter("token");
//                if(StringUtils.isNotBlank(newToken)){
//                    //如果不为空 把取到的新的覆盖掉旧的
//                    token=newToken;
//                }
//
//                // 没有LoginRequired注解不拦截
//                //获取注解的值
//                boolean value = methodAnnotation.value();
//                //判断是true还是false
//                if(value){
//                    //如果为true
//                    //System.out.println("拦截必须登录");
//                }
//                //System.out.println("拦截不必登录");
//            }
//            //System.out.println("没有拦截");
//        }
//        //System.out.println("前段资源不拦截");
        return true;
    }
}
