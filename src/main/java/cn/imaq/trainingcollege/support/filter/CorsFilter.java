package cn.imaq.trainingcollege.support.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        res.addHeader("Autumn-Thread", Thread.currentThread().getName());
        res.addHeader("Access-Control-Allow-Origin", "*");
        if (req.getMethod().equals("OPTIONS")) {
            res.setStatus(200);
            res.addIntHeader("Content-Length", 0);
            res.addHeader("Access-Control-Allow-Headers", "Content-Type, X-Auth-Token");
            return;
        }
        chain.doFilter(req, res);
    }
}
