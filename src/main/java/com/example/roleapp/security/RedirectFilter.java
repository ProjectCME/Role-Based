package com.example.roleapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class RedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain)
            throws ServletException, IOException {

        String uri = req.getRequestURI();

        // Ignore error pages
        if (uri.equals("/403") || uri.startsWith("/error")) {
            chain.doFilter(req, res);
            return;
        }

        // Only intercept root URL
        if (uri.equals("/")) {

            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("userRole") == null) {
                res.sendRedirect("/login");
                return;
            }

            String role = (String) session.getAttribute("userRole");

            switch (role) {
                case "ADMIN":
                    res.sendRedirect("/admin/dashboard");
                    return;
                case "TEACHER":
                    res.sendRedirect("/teacher/dashboard");
                    return;
                case "STUDENT":
                    res.sendRedirect("/student/dashboard");
                    return;
            }
        }

        chain.doFilter(req, res);
    }
}
