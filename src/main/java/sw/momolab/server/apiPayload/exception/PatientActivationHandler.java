package sw.momolab.server.apiPayload.exception;

import sw.momolab.server.apiPayload.code.BaseErrorCode;

public class PatientActivationHandler extends GeneralException {
    public PatientActivationHandler(BaseErrorCode code) {
        super(code);
    }
}