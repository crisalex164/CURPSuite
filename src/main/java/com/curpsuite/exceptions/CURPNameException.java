package com.curpsuite.exceptions;

/**
 * El nombre provisto no corresponde a la CURP.
 */
public class CURPNameException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPNameException(String message) {
        super(message);
    }
}