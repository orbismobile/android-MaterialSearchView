package com.orbis.samplematerialsearchview;

/**
 * Created by Carlos Vargas on 6/24/17.
 */

public class AlarmEntity {

    private String message;

    public AlarmEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
