package com.legacybanking.legacyBankingAPI.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



public class JwtVerifierFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String key = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try{
                Claims claim = Jwts.parserBuilder().setSigningKey(key.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(authHeader).getBody();
                String username = String.valueOf(claim.get("username"));
                String authorities = String.valueOf(claim.get("authorities"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new BadCredentialsException("Invalid Token");
            }
        }
        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/auth/login");
    }
}
