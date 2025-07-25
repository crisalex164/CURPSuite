package com.curpsuite.exceptions;

/**
 * El dígito verificador de la CURP no es el calculado.
 */
public class CURPVerificationException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPVerificationException(String message) {
        super(message);
    }
}
