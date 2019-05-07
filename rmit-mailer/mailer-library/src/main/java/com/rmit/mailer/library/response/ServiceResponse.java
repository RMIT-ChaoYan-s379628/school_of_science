
package com.rmit.mailer.library.response;

import java.io.Serializable;

public class ServiceResponse implements Serializable {

    private static final long serialVersionUID = 4260241436133993926L;

    private int               code;

    private String            message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
