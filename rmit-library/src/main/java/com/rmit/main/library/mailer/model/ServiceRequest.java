package com.rmit.main.library.mailer.model;

import java.io.Serializable;

public abstract class ServiceRequest implements Serializable {

    private static final long serialVersionUID = -7117975726723609148L;

    public abstract boolean isValid();

}
