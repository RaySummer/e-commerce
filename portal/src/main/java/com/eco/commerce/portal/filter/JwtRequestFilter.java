package com.eco.commerce.portal.filter;

import com.eco.commerce.core.module.member.dto.MemberDto;
import com.eco.commerce.core.utils.WebThreadLocal;
import com.eco.commerce.portal.module.member.dto.vo.MemberVO;
import com.eco.commerce.portal.module.member.service.JwtUserDetailsService;
import com.eco.commerce.portal.module.member.service.MemberService;
import com.eco.commerce.portal.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ray
 * @since 2023/2/16
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        try {
            final String requestTokenHeader = request.getHeader("Authorization");
            WebThreadLocal.init();
            String username = null;
            String jwtToken = null;
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
            if (requestTokenHeader != null && requestTokenHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    logger.warn("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    logger.warn("JWT Token has expired");

                    DefaultClaims claims = (DefaultClaims) e.getClaims();
                    Date expiration = claims.getExpiration();

                    String isRefreshToken = request.getHeader("isRefreshToken");
                    String requestURL = request.getRequestURL().toString();
                    // allow for Refresh Token creation if following conditions are true.
                    if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                        if (!jwtUtil.isTokenIdle(expiration)) {
                            allowForRefreshToken(e, request);
                        } else {
                            logger.warn("Exceed idle time. Can never be used to generate refresh token");
                            request.setAttribute("exception", "----Exceed idle time. Can never be used to generate refresh token.");
                        }
                    } else {
                        request.setAttribute("exception", e);
                    }
                }
            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }

            //Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                // if token is valid configure Spring Security to manually set authentication
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    MemberVO memberVO = memberService.getMemberVOByAccount(userDetails.getUsername());
                    MemberDto memberDto = new MemberDto();
                    BeanUtils.copyProperties(memberVO, memberDto);
                    WebThreadLocal.setMember(memberDto);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            WebThreadLocal.remove();
        }
    }


    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }

}
