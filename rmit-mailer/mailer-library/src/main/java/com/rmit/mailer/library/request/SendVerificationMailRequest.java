
package com.rmit.mailer.library.request;

public class SendVerificationMailRequest extends ServiceRequest {

    private static final long serialVersionUID = 3755556342516984170L;

    private String            to;

    private String            token;

    @Override
    public boolean isValid() {
        if (this.to == null || this.to.isEmpty())
            return false;
        if (this.token == null || this.token.isEmpty())
            return false;
        return true;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
