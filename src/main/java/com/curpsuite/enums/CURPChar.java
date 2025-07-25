package com.curpsuite.enums;

public enum CURPChar {

    // Primer Apellido
    SURNAME_A_CHAR(0),
    SURNAME_A_VOWEL(1),
    SURNAME_A_CONSONANT(13),

    // Segundo Apellido
    SURNAME_B_CHAR(2),
    SURNAME_B_CONSONANT(14),

    // Nombre de Pila
    NAME_CHAR(3),
    NAME_CONSONANT(15),

    // Fecha de Nacimiento
    YEAR_0(4),
    YEAR_1(5),
    MONTH_0(6),
    MONTH_1(7),
    DAY_0(8),
    DAY_1(9),

    // Sexo y Entidad Federativa de Nacimiento
    SEX(10),
    REGION_0(11),
    REGION_1(12),

    // Homoclave
    HOMONYMY(16),
    VERIFICATION(17);


    private final int value;
    CURPChar(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
