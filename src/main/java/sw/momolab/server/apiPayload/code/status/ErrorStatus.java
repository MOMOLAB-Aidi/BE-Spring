package sw.momolab.server.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sw.momolab.server.apiPayload.code.BaseErrorCode;
import sw.momolab.server.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_401_01", "아이디 또는 비밀번호가 일치하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH_401_02", "만료된 JWT 토큰입니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "AUTH_401_03", "유효하지 않은 JWT 서명 또는 형식입니다."),
    TOKEN_GENERAL_ERROR(HttpStatus.UNAUTHORIZED, "AUTH_401_04", "기타 토큰 인증 오류입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401_05", "인증 정보가 필요합니다."),

    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH_401_06", "유효하지 않은 Refresh Token입니다."),

    USER_STATUS_INACTIVE(HttpStatus.FORBIDDEN, "USER_403_01", "탈퇴한 회원입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
