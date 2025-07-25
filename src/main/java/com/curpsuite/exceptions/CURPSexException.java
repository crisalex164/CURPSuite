package com.curpsuite.exceptions;

/**
 * El sexo indicado en la CURP no es válido.
 */
public class CURPSexException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPSexException(String message) {
        super(message);
    }
}
