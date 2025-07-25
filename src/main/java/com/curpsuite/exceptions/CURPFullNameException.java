package com.curpsuite.exceptions;

/**
 * El nombre completo provisto no corresponde a la CURP.
 */
public class CURPFullNameException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPFullNameException(String message) {
        super(message);
    }
}
