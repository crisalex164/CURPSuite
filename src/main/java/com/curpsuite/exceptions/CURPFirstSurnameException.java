package com.curpsuite.exceptions;

/**
 * El primer apellido provisto no corresponde a la CURP.
 */
public class CURPFirstSurnameException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPFirstSurnameException(String message) {
        super(message);
    }
}
