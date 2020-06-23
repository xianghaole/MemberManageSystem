package cn.lger.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object member = request.getSession().getAttribute("member");
        //System.out.println(user);
        if(member==null){
            request.setAttribute("error","没有权限，请先登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else{
            return true;
        }

    }
}
