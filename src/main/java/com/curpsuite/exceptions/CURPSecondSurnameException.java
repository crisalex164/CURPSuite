package com.curpsuite.exceptions;

/**
 * El segundo apellido provisto no corresponde a la CURP.
 */
public class CURPSecondSurnameException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPSecondSurnameException(String message) {
        super(message);
    }
}
