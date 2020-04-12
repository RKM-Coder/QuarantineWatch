package com.goalsr.homequarantineTracker.resposemodel.emergency;

import com.goalsr.homequarantineTracker.resposemodel.ReqHeader;
import com.goalsr.homequarantineTracker.resposemodel.ReqTrailer;

public class ReqEmegency {

    private ReqHeader header;
    private ReqTrailer trailer;
    private ReqBodyEmergency body;

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

    public ReqBodyEmergency getBody() {
        return body;
    }

    public void setBody(ReqBodyEmergency body) {
        this.body = body;
    }
}
