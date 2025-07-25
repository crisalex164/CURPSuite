package com.curpsuite.exceptions;
/**
 * La entidad federativa indicada en la CURP no es válida.
 */
public class CURPRegionException extends CURPValueException {
    private static final long serialVersionUID = 1L;

    public CURPRegionException(String message) {
        super(message);
    }
}