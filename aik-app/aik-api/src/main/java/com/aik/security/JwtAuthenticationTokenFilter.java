package com.aik.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.doctor-sign}")
    private String jwtDoctorSign;

    @Value("${jwt.user-sign}")
    private String jwtUserSign;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);

        // 打印header
//        String contentType = "";
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            logger.debug("request header: " + name + "[" + request.getHeader(name) + "]" );
//            if (name.equals("content-type")) {
//                contentType = request.getHeader(name);
//            }
//        }

        // 获取multipart/form-data 请求正文
//        if (contentType.contains("multipart/form-data")) {
//            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//            MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
//            multipartResolver.cleanupMultipart(multiRequest);
//            Enumeration<String> paramNames = multiRequest.getParameterNames();
//            while (paramNames.hasMoreElements()) {
//                String name = paramNames.nextElement();
//                logger.debug("request param: " + name + "[" + multiRequest.getParameter(name) + "]" );
//            }
//
//            MultiValueMap<String, MultipartFile> fileMap = multiRequest.getMultiFileMap();
//            Set<String> set = fileMap.keySet();
//            Iterator<String> it = set.iterator();
//            while(it.hasNext()) {
//                String name = it.next();
//                List<MultipartFile> files = fileMap.get(name);
//                logger.debug("request file name: " + name);
//                for (MultipartFile multipartFile : files) {
//                    logger.debug("request file: size[" + multipartFile.getSize() + "] name[" + multipartFile.getName() +
//                            multipartFile.getOriginalFilename() + "]");
//                }
//            }
//        }

        logger.debug("checking authentication authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            String usertype = jwtTokenUtil.getUserTypeFromToken(authToken);

            logger.info("checking authentication " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
                // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                // 本例中，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询
                // 但简单验证的话，你可以采用直接验证token是否合法来避免昂贵的数据查询
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username + usertype);

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    AuthUserDetailsThreadLocal.setCurrentUser((JwtUser)userDetails);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
