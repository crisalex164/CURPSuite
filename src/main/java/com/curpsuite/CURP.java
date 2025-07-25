package com.curpsuite;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.curpsuite.data.Altisonantes;
import com.curpsuite.data.Estados;
import com.curpsuite.data.RegionData;
import com.curpsuite.enums.CURPChar;
import com.curpsuite.enums.Sexo;
import com.curpsuite.exceptions.*;
import com.curpsuite.utils.WordFeautres;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

/**
 * Realiza extracción de datos y validación de una CURP
 * (Clave Única de Registro de Población).
 *
 * Uso:
 * <pre>
 * {@code
 * CURP curp = new CURP("SABC560626MDFLRN01");
 * System.out.println(curp.getFechaNacimiento());
 * }
 * </pre>
 *
 * @author Jacob Sánchez Pérez
 * @version 2.6.1
 */
public class CURP {
    private static final int LENGTH = 18;
    private static final String CHARSET = "0123456789ABCDEFGHIJKLMN\u00d1OPQRSTUVWXYZ"; // Incluyendo la Ñ

    private static final List<String> IGNORED_WORDS = Arrays.asList(
            "DA", "DAS", "DE", "DEL", "DER", "DI", "DIE", "DD",
            "EL", "LA", "LOS", "LAS", "LE", "LES", "MAC", "MC",
            "VAN", "VON", "Y");

    private static final List<String> SPECIAL_CHARS = Arrays.asList("/", "-", ".", "'", "'");
    private static final List<String> IGNORED_NAMES = Arrays.asList("MARIA", "MA", "MA.", "JOSE", "J", "J.");

    // Sexos
    private static final Map<String, Sexo> SEXES = new HashMap<>();
    static {
        SEXES.put("H", Sexo.HOMBRE);
        SEXES.put("M", Sexo.MUJER);
    }

    // Para detectar si una CURP es inválida debido a estar sin censura,
    // crear lista de palabras sin censura
    private static final List<String> INC_UNCENSORED = new ArrayList<>();
    static {
        for (Map.Entry<String, String[]> entry : Altisonantes.getAltisonantes().entrySet()) {
            String key = entry.getKey();
            String[] vowels = entry.getValue();
            for (String vowel : vowels) {
                INC_UNCENSORED.add(key.charAt(0) + vowel + key.substring(2));
            }
        }
    }

    private final String curp;
    private LocalDate birthDate;
    private Sexo sex;
    private RegionData birthPlace;
    private String name;
    private String firstSurname;
    private String secondSurname;

    /**
     * Construye una CURP.
     *
     * Si sólo se proporciona un nombre completo, se dividirá de acuerdo a la CURP.
     * Si se proporciona el nombre por partes, se usarán en lugar del nombre completo.
     * Sólo se validarán las partes que se proporcionen.
     *
     * @param curp Una CURP de 18 caracteres.
     * @param nombre Nombre de pila de la persona.
     * @param primerApellido Primer apellido (paterno) de la persona.
     * @param segundoApellido Segundo apellido (materno) de la persona.
     * @param nombreCompleto Nombre completo de la persona.
     */
    public CURP(String curp, String nombre, String primerApellido,
                String segundoApellido, String nombreCompleto) {
        this.curp = curp;

        if (curp.length() != LENGTH) {
            throw new CURPLengthException("La CURP no tiene el tamaño correcto");
        }

        if (!validateVerify()) {
            throw new CURPVerificationException("El dígito verificador no coincide con la CURP");
        }

        // Fecha de nacimiento
        parseBirthDate();
        // Sexo
        parseSex();
        // Estado de la república
        parseRegion();

        // Validar caracteres restantes
        if (!validateNameChars()) {
            throw new CURPValueException("Los caracteres del nombre/apellidos contienen errores");
        }

        if (nombre != null) {
            if (nombreValido(nombre)) {
                this.name = nombre.toUpperCase();
            } else {
                throw new CURPNameException("El nombre de pila no coincide con la CURP");
            }
        }

        if (primerApellido != null) {
            if (primerApellidoValido(primerApellido)) {
                this.firstSurname = primerApellido.toUpperCase();
            } else {
                throw new CURPFirstSurnameException("El primer apellido no coincide con la CURP");
            }
        }

        if (segundoApellido != null) {
            if (segundoApellidoValido(segundoApellido)) {
                this.secondSurname = segundoApellido.toUpperCase();
            } else {
                throw new CURPSecondSurnameException("El segundo apellido no coincide con la CURP");
            }
        }

        boolean noPieces = nombre == null && primerApellido == null && segundoApellido == null;

        if (noPieces && nombreCompleto != null) {
            String[] names = nombreCompletoValido(nombreCompleto);

            if (names != null) {
                this.name = names[0].toUpperCase();
                this.firstSurname = names[1].toUpperCase();
                this.secondSurname = names[2].toUpperCase();
            } else {
                throw new CURPFullNameException("El nombre completo no parece coincidir con la CURP");
            }
        }
    }

    /**
     * Constructor simplificado que solo recibe la CURP.
     *
     * @param curp Una CURP de 18 caracteres.
     */
    public CURP(String curp) {
        this(curp, null, null, null, null);
    }

    /**
     * Verifica que una CURP sea válida para cierto nombre de pila.
     *
     * @param name Nombre de pila para validar.
     * @return true si el nombre es válido para esta CURP
     */
    public boolean nombreValido(String name) {
        // Remover primer nombre si este es muy común
        String[] pieces = name.toUpperCase().split(" ");

        // Tal vez sea necesario omitir palabras ignoradas (De, Del, Etc.)
        if (pieces.length > 1 && IGNORED_NAMES.contains(normalize(pieces[0]))) {
            String[] newPieces = new String[pieces.length - 1];
            System.arraycopy(pieces, 1, newPieces, 0, pieces.length - 1);
            pieces = newPieces;
        }

        WordFeautres wf = new WordFeautres(String.join(" ", pieces),
                IGNORED_WORDS, SPECIAL_CHARS);

        boolean valid = (curp.charAt(CURPChar.NAME_CHAR.getValue()) == wf.getChar()) &&
                        (curp.charAt(CURPChar.NAME_CONSONANT.getValue()) == wf.getConsonant());
        return valid;
    }

    /**
     * Verifica que una CURP sea válida para cierto primer apellido.
     *
     * @param primerApellido Primer apellido (usualmente paterno) para validar.
     * @return true si el primer apellido es válido para esta CURP
     */
    public boolean primerApellidoValido(String primerApellido) {
        String curpStart = curp.substring(0, CURPChar.NAME_CHAR.getValue() + 1);

        WordFeautres wf = new WordFeautres(primerApellido, IGNORED_WORDS, SPECIAL_CHARS);

        boolean valid = (curp.charAt(CURPChar.SURNAME_A_CHAR.getValue()) == wf.getChar()) &&
                        (curp.charAt(CURPChar.SURNAME_A_CONSONANT.getValue()) == wf.getConsonant());

        if (valid) {
            valid = curp.charAt(CURPChar.SURNAME_A_VOWEL.getValue()) == wf.getVowel();

            // Buscar principio de la CURP en la lista de palabras inconvenientes
            if (Altisonantes.getAltisonantes().containsKey(curpStart)) {
                // Usar las vocales reales para determinar si la CURP corresponde al apellido
                for (String vowel : Altisonantes.getAltisonantes().get(curpStart)) {
                    valid = valid || (vowel.charAt(0) == wf.getVowel());
                }
            }
        }

        return valid;
    }

    /**
     * Verifica que una CURP sea válida para cierto segundo apellido.
     *
     * @param segundoApellido Segundo apellido (usualmente materno) para validar.
     * @return true si el segundo apellido es válido para esta CURP
     */
    public boolean segundoApellidoValido(String segundoApellido) {
        WordFeautres wf = new WordFeautres(segundoApellido, IGNORED_WORDS, SPECIAL_CHARS);

        boolean valid = (curp.charAt(CURPChar.SURNAME_B_CHAR.getValue()) == wf.getChar()) &&
                        (curp.charAt(CURPChar.SURNAME_B_CONSONANT.getValue()) == wf.getConsonant());
        return valid;
    }

    /**
     * Utiliza un nombre completo para validar la CURP.
     *
     * @param nombreCompleto Nombre completo para validar.
     * @return Una arreglo con el nombre por partes, o null si el nombre no corresponde.
     */
    public String[] nombreCompletoValido(String nombreCompleto) {
        enum NameParseState {
            NONE, GIVEN_NAMES, FIRST_SURNAME, SECOND_SURNAME
        }

        Map<NameParseState, List<String>> names = new HashMap<>();
        for (NameParseState state : NameParseState.values()) {
            names.put(state, new ArrayList<>());
        }

        NameParseState state = NameParseState.NONE;
        List<String> ignoredBuffer = new ArrayList<>();

        String[] words = nombreCompleto.split(" ");
        for (String word : words) {
            if (!IGNORED_WORDS.contains(normalize(word.toUpperCase()))) {
                boolean valid;
                switch (state) {
                    case NONE:
                        valid = nombreValido(word);
                        if (valid) {
                            state = NameParseState.GIVEN_NAMES;
                        } else if (!IGNORED_NAMES.contains(normalize(word.toUpperCase()))) {
                            return null;
                        }
                        break;
                    case GIVEN_NAMES:
                        valid = primerApellidoValido(word);
                        if (valid) {
                            state = NameParseState.FIRST_SURNAME;
                        }
                        break;
                    case FIRST_SURNAME:
                        valid = segundoApellidoValido(word);
                        if (valid) {
                            state = NameParseState.SECOND_SURNAME;
                        }
                        break;
                    case SECOND_SURNAME:
                        return null; // No debería haber más palabras después del segundo apellido
                }

                // Agregar palabras ignoradas guardadas a la parte actual
                if (!ignoredBuffer.isEmpty()) {
                    names.get(state).addAll(ignoredBuffer);
                    ignoredBuffer.clear();
                }
                names.get(state).add(word);
            } else {
                ignoredBuffer.add(word);
            }
        }

        names.get(state).addAll(ignoredBuffer);

        boolean valid = state == NameParseState.SECOND_SURNAME;

        if (state == NameParseState.FIRST_SURNAME) {
            valid = isSegundoApellidoVacio();
        } else if (state == NameParseState.GIVEN_NAMES) {
            valid = isPrimerApellidoVacio();
        }

        if (valid) {
            List<String> givenNames = names.get(NameParseState.NONE);
            givenNames.addAll(names.get(NameParseState.GIVEN_NAMES));

            String[] result = new String[3];
            result[0] = String.join(" ", givenNames);
            result[1] = String.join(" ", names.get(NameParseState.FIRST_SURNAME));
            result[2] = String.join(" ", names.get(NameParseState.SECOND_SURNAME));

            return result;
        }

        return null;
    }

    /**
     * Valida que los caracteres correspondientes al nombre y apellidos
     * estén dentro del espacio correcto.
     */
    private boolean validateNameChars() {
        String consonants = WordFeautres.CONSONANTS;
        String vowels = WordFeautres.VOWELS + "X";

        int[][] nameChars = {
                {CURPChar.NAME_CHAR.getValue(), CURPChar.NAME_CONSONANT.getValue()},
                {CURPChar.SURNAME_A_CHAR.getValue(), CURPChar.SURNAME_A_CONSONANT.getValue()},
                {CURPChar.SURNAME_B_CHAR.getValue(), CURPChar.SURNAME_B_CONSONANT.getValue()}
        };

        boolean valid = vowels.indexOf(curp.charAt(CURPChar.SURNAME_A_VOWEL.getValue())) >= 0;

        for (int[] pair : nameChars) {
            int charPos = pair[0];
            int consonantPos = pair[1];

            char ch = curp.charAt(charPos);
            char cons = curp.charAt(consonantPos);

            valid = valid && (Character.isUpperCase(ch) && consonants.indexOf(cons) >= 0);
        }

        if (INC_UNCENSORED.contains(curp.substring(0, CURPChar.NAME_CHAR.getValue() + 1))) {
            valid = false;
        }

        return valid;
    }

    /**
     * Obtiene la fecha de nacimiento de la CURP.
     *
     * @return La fecha de nacimiento indicada en la CURP
     * @throws CURPDateException La fecha de nacimiento no pudo ser construída
     */
    private LocalDate parseBirthDate() {
        // Homonímia
        // [0-9] para personas nacidas hasta el 1999
        // [A-Z] para personas nacidas desde el 2000
        char homonymy = curp.charAt(CURPChar.HOMONYMY.getValue());
        boolean before2k = Character.isDigit(homonymy);

        // Día y mes de nacimiento
        try {
            int day = Integer.parseInt(curp.substring(CURPChar.DAY_0.getValue(), CURPChar.DAY_1.getValue() + 1));
            int month = Integer.parseInt(curp.substring(CURPChar.MONTH_0.getValue(), CURPChar.MONTH_1.getValue() + 1));
            int year = Integer.parseInt(curp.substring(CURPChar.YEAR_0.getValue(), CURPChar.YEAR_1.getValue() + 1));

            // Año y siglo actual
            int currentYear = Year.now().getValue();
            int century = currentYear / 100;

            // Asume que cualquier año mayor al actual
            // es en realidad del siglo pasado
            if (year > (currentYear % 100)) {
                century -= 1;
            }

            // Aunque no necesariamente cierto,
            // es probablemente la mejor opción
            if (before2k) {
                century = 19;
            } else if (century == 19) {
                century = 20;
            }

            year += (century * 100);

            // Regresar con error si la fecha es incorrecta
            try {
                birthDate = LocalDate.of(year, month, day);
            } catch (Exception e) {
                throw new CURPDateException("La fecha de nacimiento es incorrecta");
            }

            return birthDate;
        } catch (NumberFormatException e) {
            throw new CURPValueException("La fecha de nacimiento contiene caracteres no numéricos");
        }
    }

    /**
     * Obtiene el sexo de la CURP.
     *
     * @return El sexo de acuerdo a ISO/IEC 5218.
     * @throws CURPSexException El sexo en la CURP es incorrecto.
     */
    private Sexo parseSex() {
        char curpSex = curp.charAt(CURPChar.SEX.getValue());
        sex = SEXES.getOrDefault(String.valueOf(curpSex), Sexo.DESCONOCIDO);

        if (sex == Sexo.DESCONOCIDO) {
            throw new CURPSexException("El sexo de la CURP no es válido");
        }

        return sex;
    }

    /**
     * Obtiene la entidad federativa de nacimiento de la CURP.
     *
     * @return Datos de la entidad federativa de nacimiento.
     * @throws CURPRegionException La CURP contiene un código de entidad incorrecto.
     */
    private RegionData parseRegion() {
        String curpRegion = curp.substring(CURPChar.REGION_0.getValue(), CURPChar.REGION_1.getValue() + 1);
        birthPlace = Estados.getEstados().get(curpRegion);

        if (birthPlace == null) {
            throw new CURPRegionException("La entidad de nacimiento es incorrecta");
        }

        return birthPlace;
    }

    /**
     * Usa el último carácter de la CURP para verificar la misma
     * de acuerdo al algoritmo oficial.
     */
    private boolean validateVerify() {
        // Código de verificación
        char verify = curp.charAt(CURPChar.VERIFICATION.getValue());
        int b37Sum = verificationSum(curp);
        // Hacer las operaciones finales
        return sumToVerifyDigit(b37Sum) == verify;
    }

    /**
     * Suma de verificación de la CURP.
     */
    private static int verificationSum(String curp) {
        try {
            int sum = 0;
            for (int i = 0; i < curp.length() - 1; i++) {
                sum += (curp.length() - i) * CHARSET.indexOf(curp.charAt(i));
            }
            // Asegurarse de que el dígito de verificación sea válido
            CHARSET.indexOf(curp.charAt(curp.length() - 1));
            return sum;
        } catch (StringIndexOutOfBoundsException e) {
            throw new CURPValueException("La CURP contiene caracteres no válidos.");
        }
    }

    /**
     * Convierte la suma a dígito verificador
     */
    private static char sumToVerifyDigit(int sum) {
        int d = sum % 10;
        d = (d == 0) ? 0 : 10 - d;
        return Character.forDigit(d, 10);
    }

    /**
     * Normaliza un string removiendo acentos.
     */
    private static String normalize(String text) {
        return java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replace('Ñ', 'X')
                .replace('ñ', 'X');
    }

    /**
     * Objeto JSON conteniendo los datos extraídos de la CURP.
     */
    public String toJson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("curp", curp);
        jsonData.put("sexo", sex);
        jsonData.put("fecha_nacimiento", birthDate);
        jsonData.put("entidad_nacimiento", birthPlace);

        if (name != null) {
            jsonData.put("nombre", name);
        }

        if (firstSurname != null) {
            jsonData.put("primer_apellido", firstSurname);
        }

        if (secondSurname != null) {
            jsonData.put("segundo_apellido", secondSurname);
        }

        return gson.toJson(jsonData);
    }

    /**
     * @return CURP con la que se construyó el objeto.
     */
    public String getCurp() {
        return curp;
    }

    /**
     * @return Nombre con el que se construyó el objeto.
     */
    public String getNombre() {
        return name;
    }

    /**
     * @return Primer apellido con el que se construyó el objeto.
     */
    public String getPrimerApellido() {
        return firstSurname;
    }

    /**
     * @return Segundo apellido con el que se construyó el objeto.
     */
    public String getSegundoApellido() {
        return secondSurname;
    }

    /**
     * @return Fecha de nacimiento extraída de la CURP.
     */
    public LocalDate getFechaNacimiento() {
        return birthDate;
    }

    /**
     * @return Sexo extraído de la CURP.
     */
    public Sexo getSexo() {
        return sex;
    }

    /**
     * @return Entidad federativa de nacimiento de la CURP.
     */
    public String getEntidad() {
        return birthPlace.getName();
    }

    /**
     * @return Código ISO de la entidad federativa de nacimiento de la CURP.
     */
    public String getEntidadIso() {
        return birthPlace.getIso();
    }

    /**
     * @return True si la CURP pertenece a alguien nacido en el extranjero.
     */
    public boolean isExtranjero() {
        return birthPlace.getIso() == null;
    }

    /**
     * @return True si la CURP puede corresponder a un primer apellido vacio.
     */
    public boolean isPrimerApellidoVacio() {
        // Asumir que si existe un segundo apellido, el primero también debe
        // existir, incluso si aparenta no hacerlo
        boolean segundoVacio = isSegundoApellidoVacio();
        return segundoVacio && primerApellidoValido("");
    }

    /**
     * @return True si la CURP puede corresponder a un segundo apellido vacio.
     */
    public boolean isSegundoApellidoVacio() {
        return segundoApellidoValido("");
    }

    @Override
    public String toString() {
        return "<CURP [" + curp.substring(0, CURPChar.NAME_CHAR.getValue() + 1) + "]>";
    }
}