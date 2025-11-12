package sw.momolab.server.apiPayload.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sw.momolab.server.apiPayload.code.ErrorReasonDTO;
import sw.momolab.server.apiPayload.code.status.ErrorStatus;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    // 인증되지 않은 사용자가 보호된 리소스에 접근하려 할 때 호출
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {

        // 1. 응답 상태 및 Content-Type 설정
        ErrorStatus errorStatus = ErrorStatus.UNAUTHORIZED;
        response.setStatus(errorStatus.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 2. ErrorStatus의 정보를 사용하여 ErrorReasonDTO 객체 생성
        ErrorReasonDTO errorReasonDTO = ErrorReasonDTO.builder()
                .httpStatus(errorStatus.getHttpStatus())
                .isSuccess(false)
                .code(errorStatus.getCode())
                .message(errorStatus.getMessage())
                .build();

        // 3. JSON으로 변환하여 응답 스트림에 쓰기
        response.getWriter().write(objectMapper.writeValueAsString(errorReasonDTO));
    }
}