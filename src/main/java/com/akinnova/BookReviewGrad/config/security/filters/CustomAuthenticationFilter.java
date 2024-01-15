//package com.akinnova.BookReviewGrad.config.security.filters;
//
//import com.akinnova.BookReviewGrad.config.security.authentication.CustomAuthentication;
//import com.akinnova.BookReviewGrad.config.security.managers.CustomAuthenticationManager;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//@Component
//@AllArgsConstructor
//public class CustomAuthenticationFilter extends OncePerRequestFilter {
//
//    private final CustomAuthenticationManager customAuthenticationManager;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        //1. create an authentication object which is not yet authenticated
//        //2. delegate the authentication object to the manager
//        //3. get the authentication object back from the manager
//        //4. if the object is authenticated then, send request to the next filter
//        String key = String.valueOf(request.getHeader("key"));
//
//        CustomAuthentication ca = new CustomAuthentication(false, key);
//
//        var a = customAuthenticationManager.authenticate(ca);
//
//        if (a.isAuthenticated()){
//            SecurityContextHolder.getContext().setAuthentication(a);
//            filterChain.doFilter(request, response); //will be used only when authentication works
//        }
//
//    }
//
//}
