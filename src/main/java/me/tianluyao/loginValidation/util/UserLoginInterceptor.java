package me.tianluyao.loginValidation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = "";

            if(request.getHeader("token") == null){
                System.err.println("session="+request.getSession());
                System.err.println("session="+request.getSession().getAttribute("token"));
                if(request.getSession().getAttribute("token") == null){
                    response.sendRedirect(request.getContextPath() + "login");
                    return false;
                }else{
                    token = request.getSession().getAttribute("token")+"";
                }
            }else{
                token = request.getHeader("token");
            }

            RedisTemplate emRedisTemplate = RedisBean.redis;
            Long expire = emRedisTemplate.getExpire(token);

            if(expire > 0){
                return true;
            }else{
                response.sendRedirect(request.getContextPath() + "login");
            }

//            response.sendRedirect(request.getContextPath() + "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    /***
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行了拦截器的postHandle方法");
    }

    /***
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("执行了拦截器的afterCompletion方法");
    }
}

