/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sherbrow
 */
public class FrontFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        return;
    }

    /* Source : http://stackoverflow.com/a/13935100/1478467 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/assets") || path.startsWith("/jsf") || path.startsWith("/jsp")) {
            chain.doFilter(request, response); // Goes to container's own default servlet.
        } else {
            request.getRequestDispatcher("/mvc" + path).forward(request, response); // Goes to controller servlet.
        }
    }

    @Override
    public void destroy() {
        return;
    }

}
