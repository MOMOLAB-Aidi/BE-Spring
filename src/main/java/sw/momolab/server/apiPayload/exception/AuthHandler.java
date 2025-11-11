package sw.momolab.server.apiPayload.exception;

import sw.momolab.server.apiPayload.code.BaseErrorCode;

public class AuthHandler extends GeneralException {
    public AuthHandler(BaseErrorCode code) {
        super(code);
    }
}