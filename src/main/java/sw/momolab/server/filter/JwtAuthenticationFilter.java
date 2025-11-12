package sw.momolab.server.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;
import sw.momolab.server.apiPayload.exception.TokenHandler;
import sw.momolab.server.service.tokenService.JwtTokenService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HEADER_AUTHORIZATION);

        // 1. JWT 토큰 추출 및 유효성 확인
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(TOKEN_PREFIX.length());
        final String userIdentifier;

        try {
            // 2. 토큰에서 사용자의 식별자를 추출
            userIdentifier = jwtTokenService.parseClaims(jwt).getSubject();
        } catch (ExpiredJwtException e) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new TokenHandler(ErrorStatus.TOKEN_EXPIRED));
            return;
        } catch (MalformedJwtException | SignatureException e) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new TokenHandler(ErrorStatus.TOKEN_INVALID));
            return;
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new TokenHandler(ErrorStatus.TOKEN_GENERAL_ERROR));
            return;
        }

        // 3. 사용자 인증 정보를 SecurityContext에 저장
        if (userIdentifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userIdentifier);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // 요청 정보 설정
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception e) {
                handlerExceptionResolver.resolveException(request, response, null,
                        new TokenHandler(ErrorStatus.TOKEN_GENERAL_ERROR));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}