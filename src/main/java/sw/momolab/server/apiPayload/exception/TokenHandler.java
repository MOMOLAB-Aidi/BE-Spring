package sw.momolab.server.apiPayload.exception;

import sw.momolab.server.apiPayload.code.BaseErrorCode;

public class TokenHandler extends GeneralException {
    public TokenHandler(BaseErrorCode code) {
        super(code);
    }
}
