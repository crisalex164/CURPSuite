package com.curpsuite.enums;

public enum Sexo {
    DESCONOCIDO(0),
    HOMBRE(1),
    MUJER(2);

    private final int value;

    Sexo(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name().charAt(0) + "";
    }
}
