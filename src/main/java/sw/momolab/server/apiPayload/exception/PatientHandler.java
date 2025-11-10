package sw.momolab.server.apiPayload.exception;

import sw.momolab.server.apiPayload.code.BaseErrorCode;

public class PatientHandler extends GeneralException {
    public PatientHandler(BaseErrorCode code) {
        super(code);
    }
}
