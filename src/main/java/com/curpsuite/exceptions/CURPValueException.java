package com.curpsuite.exceptions;

/**
 * Indica que la validación de la CURP presentó errores generales.
 */
public class CURPValueException extends CURPException {
    private static final long serialVersionUID = 1L;

    public CURPValueException(String message) {
        super(message);
    }
}