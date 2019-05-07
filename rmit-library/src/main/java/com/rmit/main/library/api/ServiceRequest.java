package com.rmit.main.library.api;

import java.io.Serializable;

public abstract class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 2976600358407360748L;

    public abstract boolean isValid();

}
