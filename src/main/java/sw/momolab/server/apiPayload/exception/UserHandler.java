package sw.momolab.server.apiPayload.exception;

import sw.momolab.server.apiPayload.code.BaseErrorCode;

public class UserHandler extends GeneralException {
    public UserHandler(BaseErrorCode code) {
        super(code);
    }
}
