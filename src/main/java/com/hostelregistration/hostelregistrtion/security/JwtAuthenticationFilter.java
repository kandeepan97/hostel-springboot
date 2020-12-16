package com.hostelregistration.hostelregistrtion.security;


import com.hostelregistration.hostelregistrtion.model.Admin;
import com.hostelregistration.hostelregistrtion.model.Student;
import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.services.CustomStudentDetailService;
import com.hostelregistration.hostelregistrtion.services.CustomWardenDetailService;
import com.hostelregistration.hostelregistrtion.services.CustomAdminDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.hostelregistration.hostelregistrtion.security.SecurityConstants.HEADER_STRING;
import static com.hostelregistration.hostelregistrtion.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomStudentDetailService customStudentDetailService;

    @Autowired
    private CustomWardenDetailService customWardenDetailService;

    @Autowired
    private CustomAdminDetailService customAdminDetailService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(request);
            if (StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)) {

                String studentid = String.valueOf(tokenProvider.getStudentidFromJwt(jwt));
                Student studentDetails = customStudentDetailService.loadUserByStudentid(studentid);


                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        studentDetails, null, Collections.emptyList()
                );


               String  email= String.valueOf(tokenProvider.getemailFromJwt(jwt));
                Warden wardenDetails = customWardenDetailService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication1 = new UsernamePasswordAuthenticationToken(
                        wardenDetails, null, Collections.emptyList()
                );

                String  adminid = String.valueOf(tokenProvider.getAdminidFromJwt(jwt));
                Admin adminDetails = customAdminDetailService.loadUserByAdminid(adminid);

                UsernamePasswordAuthenticationToken authentication2 = new UsernamePasswordAuthenticationToken(
                        adminDetails, null, Collections.emptyList()
                );


                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                authentication1.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication1);

                authentication2.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication2);
            }
        }catch (Exception ex){
           logger.error("Could not set a user authentication in security context ",ex);
        }

        filterChain.doFilter(request,response);
    }
    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(bearerToken)&&bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7,bearerToken.length());
        }
        return bearerToken;
    }
}
