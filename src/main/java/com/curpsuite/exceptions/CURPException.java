package com.curpsuite.exceptions;


public class CURPException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Construye una nueva CURPException sin mensaje.
     */
    public CURPException() {
        super();
    }

    /**
     * Construye una nueva CURPException con el mensaje especificado.
     *
     * @param message El mensaje de error
     */
    public CURPException(String message) {
        super(message);
    }

    /**
     * Construye una nueva CURPException con el mensaje y causa especificados.
     *
     * @param message El mensaje de error
     * @param cause La causa de la excepción
     */
    public CURPException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construye una nueva CURPException con la causa especificada.
     *
     * @param cause La causa de la excepción
     */
    public CURPException(Throwable cause) {
        super(cause);
    }
}
