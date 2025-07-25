package com.curpsuite.exceptions;

/**
 * La fecha indicada en la CURP es incorrecta.
 */
public class CURPDateException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPDateException(String message) {
        super(message);
    }
}
