package sw.momolab.server.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sw.momolab.server.apiPayload.code.BaseErrorCode;
import sw.momolab.server.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public GeneralException(BaseErrorCode code, Throwable cause) {
        super(messageOrNull(code), cause);
        this.code = code;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }

    private static String messageOrNull(BaseErrorCode code) {
        try {
            ErrorReasonDTO reason = code.getReason();
            return reason != null ? reason.getMessage() : null;
        } catch (Exception ignore) {
            return null;
        }
    }
}
