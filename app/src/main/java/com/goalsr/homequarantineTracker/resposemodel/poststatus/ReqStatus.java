package com.goalsr.homequarantineTracker.resposemodel.poststatus;

import com.goalsr.homequarantineTracker.resposemodel.ReqHeader;
import com.goalsr.homequarantineTracker.resposemodel.ReqTrailer;

import java.util.ArrayList;

public class ReqStatus {

    private ReqHeader header;
    private ReqTrailer trailer;
    private UserTracker body;

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

    public UserTracker getBody() {
        return body;
    }

    public void setBody(UserTracker body) {
        this.body = body;
    }
}
