package com.spring.nbcijo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.nbcijo.jwt.JwtUtil;
import com.spring.nbcijo.repository.RefreshTokenBlacklistRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenBlacklistRepository refreshTokenBlacklistRepository;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService,
        RefreshTokenBlacklistRepository refreshTokenBlacklistRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.refreshTokenBlacklistRepository = refreshTokenBlacklistRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
        FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(req);
        if (!StringUtils.hasText(tokenValue)) {
            filterChain.doFilter(req, res);
            return;
        }
        // Access 토큰 유효성 검사, 만료된 토큰일 경우 다음 조건문 실행
        if (!StringUtils.hasText(tokenValue) || !jwtUtil.validateAccessToken(tokenValue)) {
            log.error("AccessToken이 유효하지 않습니다");
            sendErrorResponse(res, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
            return;
        }
        // 만료된 토큰일 경우 RefreshToken 검사
        if (jwtUtil.isAccessTokenExpired(tokenValue)) {
            // RefreshToken을 쿠키에서 추출 후 유효성 검사
            String refreshTokenValue = jwtUtil.getRefreshTokenFromCookie(req);
            // 토큰이 없거나 유효하지 않으면 에러 반환
            if (!StringUtils.hasText(refreshTokenValue) || !jwtUtil.validateRefreshToken(
                refreshTokenValue)) {
                log.error("RefreshToken이 유효하지 않습니다.");
                sendErrorResponse(res, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
                return;
            }

            // 토큰이 블랙리스트에 있는지 확인
            if (isTokenInBlacklist(refreshTokenValue)) {
                log.error("RefreshToken이 블랙리스트에 등록되어 있습니다.");
                sendErrorResponse(res, "로그아웃된 토큰입니다.", HttpStatus.UNAUTHORIZED);
                return;
            }

            // 새로운 accessToken 생성
            log.info("새로운 accessToken 생성");
            String newAccessToken = jwtUtil.generateAccessTokenFromRefreshToken(refreshTokenValue);
            tokenValue = newAccessToken.substring(7);
            // 새로운 accessToken을 클라이언트에 반환
            res.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
        }

        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

        try {
            setAuthentication(info.getSubject());
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }

        filterChain.doFilter(req, res);
    }

    // 오류 발생시 에러메세지 전달
    private void sendErrorResponse(HttpServletResponse res, String errorMessage, HttpStatus status)
        throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> response = new HashMap<>();
        response.put("message", errorMessage);
        res.getWriter().write(objectMapper.writeValueAsString(response));
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    // Refresh Token으로 Access Token 재발급 전 Refresh Token 블랙리스트 확인
    private boolean isTokenInBlacklist(String refreshTokenValue) {
        return refreshTokenBlacklistRepository.existsByRefreshToken(refreshTokenValue);
    }
}
