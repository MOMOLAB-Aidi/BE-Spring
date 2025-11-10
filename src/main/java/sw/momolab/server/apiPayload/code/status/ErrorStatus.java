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

    PATIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PATIENT_404_01", "환자를 찾을 수 없습니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "PATIENT_ACTIVATION_401_01", "유효하지 않거나 만료된 토큰입니다."),
    VALID_TOKEN_ALREADY_EXISTS(HttpStatus.CONFLICT, "PATIENT_ACTIVATION_409_01", "유효한 토큰이 이미 존재합니다."),

    PASSWORD_MUST_INCLUDE_NUMBER(HttpStatus.BAD_REQUEST, "PATIENT_404_02", "비밀번호에는 최소 한 개의 숫자가 포함되어야 합니다."),

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
