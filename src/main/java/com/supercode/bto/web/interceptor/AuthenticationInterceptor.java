package com.supercode.bto.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.supercode.bto.web.entity.RzRyxxb;
import com.supercode.bto.web.pojos.restful.RestResult;
import com.supercode.bto.web.pojos.restful.ResultCodeEnum;
import com.supercode.bto.web.service.IRzRyxxbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author pengyongbo
 * @version 1.0
 * @description: TODO
 * @date 2021/7/8 19:55
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    IRzRyxxbService rzRyxxbService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    try{
                        RestResult res = new RestResult(ResultCodeEnum.UN_LOGIN,"无token，请重新登录");
                        String json = JSON.toJSONString(res);
                        httpServletResponse.setContentType("application/json");
                        out = httpServletResponse.getWriter();
                        // 返回json信息给前端
                        out.append(json);
                        out.flush();
                        return false;
                    } catch (Exception e){
                        e.printStackTrace();
                        httpServletResponse.sendError(500);
                        return false;
                    }
                }
                // 获取 token 中的 user id
                String rybh;
                try {
                    rybh = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    try{
                        RestResult res = new RestResult(ResultCodeEnum.DATA_EXCEPTION,"数据异常！");
                        String json = JSON.toJSONString(res);
                        httpServletResponse.setContentType("application/json");
                        out = httpServletResponse.getWriter();
                        // 返回json信息给前端
                        out.append(json);
                        out.flush();
                        return false;
                    } catch (Exception e){
                        e.printStackTrace();
                        httpServletResponse.sendError(500);
                        return false;
                    }
                }
                List<RzRyxxb> rzRyxxbList = rzRyxxbService.selectByRybh(rybh);
                if (rzRyxxbList == null) {
                    try{
                        RestResult res = new RestResult(ResultCodeEnum.DATA_NON_EXISTENT,"用户不存在，请重新登录");
                        String json = JSON.toJSONString(res);
                        httpServletResponse.setContentType("application/json");
                        out = httpServletResponse.getWriter();
                        // 返回json信息给前端
                        out.append(json);
                        out.flush();
                        return false;
                    } catch (Exception e){
                        e.printStackTrace();
                        httpServletResponse.sendError(500);
                        return false;
                    }
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(rzRyxxbList.get(0).getRy_mm())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    try{
                        RestResult res = new RestResult(ResultCodeEnum.DATA_EXCEPTION,"数据异常！");
                        String json = JSON.toJSONString(res);
                        httpServletResponse.setContentType("application/json");
                        out = httpServletResponse.getWriter();
                        // 返回json信息给前端
                        out.append(json);
                        out.flush();
                        return false;
                    } catch (Exception e1){
                        e1.printStackTrace();
                        httpServletResponse.sendError(500);
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

