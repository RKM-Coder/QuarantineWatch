package com.goalsr.homequarantineTracker.resposemodel.otpvalidlogin;

import com.goalsr.homequarantineTracker.resposemodel.ReqHeader;
import com.goalsr.homequarantineTracker.resposemodel.ReqTrailer;

public class ReqOtpValid {
    private ReqOTpVAlidBody body;
    private ReqHeader header;
    private ReqTrailer trailer;

    public ReqOTpVAlidBody getBody() {
        return body;
    }

    public void setBody(ReqOTpVAlidBody body) {
        this.body = body;
    }

    public ReqHeader getHeader() {
        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public ReqTrailer getTrailer() {
        return trailer;
    }

    public void setTrailer(ReqTrailer trailer) {
        this.trailer = trailer;
    }
}
