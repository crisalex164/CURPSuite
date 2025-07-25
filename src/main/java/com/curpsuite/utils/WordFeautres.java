package com.curpsuite.utils;

import java.text.Normalizer;
import java.util.List;

public class WordFeautres {
    public static final String VOWELS = "AEIOU";
    public static final String CONSONANTS = "BCDFGHJKLMNÑPQRSTVWXYZ";

    private final char character;
    private final char vowel;
    private final char consonant;

    /**
     * Construye un objeto WordFeatures para extraer características de una palabra.
     *
     * @param word Palabra a analizar
     * @param ignoredWords Lista de palabras que no se usarán para el análisis
     *                     si no es absolutamente necesario
     * @param specialChars Lista de caracteres que sirven como separador de palabras
     */
    public WordFeautres(String word, List<String> ignoredWords, List<String> specialChars) {
        char character1;
        character1 = 'X';

        // Reemplazar Ñ's y normalizar
        word = normalizeWord(word.toUpperCase());

        // Reemplazar caracteres especiales con espacios
        for (String c : specialChars) {
            word = word.replace(c, " ");
        }

        // Remover preposiciones, conjunciones, etc.
        String[] pieces = word.split("\\s+");

        if (pieces.length > 0) {
            // Preservar última palabra
            StringBuilder sb = new StringBuilder();

            // Agregar todas las palabras que no son ignoradas excepto la última
            for (int i = 0; i < pieces.length - 1; i++) {
                if (!ignoredWords.contains(normalizeWord(pieces[i]))) {
                    sb.append(pieces[i]).append(" ");
                }
            }

            // Siempre agregar la última palabra
            sb.append(pieces[pieces.length - 1]);

            String processedWord = sb.toString().trim();
            pieces = processedWord.split("\\s+");

            // Usar primera palabra
            word = pieces[0];
            character1 = word.charAt(0);
        } else {
            word = "";
        }

        // Asegurarse que word tenga al menos 1 caracter para evitar errores
        this.character = character1;
        if (word.length() > 1) {
            this.vowel = findVowel(word.substring(1));
            this.consonant = findConsonant(word.substring(1));
        } else {
            this.vowel = 'X';
            this.consonant = 'X';
        }
    }

    /**
     * Normaliza una palabra eliminando acentos y reemplazando "Ñ" con "X".
     *
     * @param word La palabra a normalizar
     * @return La palabra normalizada
     */
    private String normalizeWord(String word) {
        String normalized = Normalizer.normalize(word, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.replace('Ñ', 'X').replace('ñ', 'X');
    }

    /**
     * Encuentra cualquier carácter de un conjunto en una palabra.
     *
     * @param charset El conjunto de caracteres a buscar
     * @param word La palabra donde buscar
     * @return El primer carácter encontrado o "X" si no hay coincidencias
     */
    private static char findChar(String charset, String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (charset.indexOf(c) >= 0) {
                return c;
            }
        }
        return 'X';
    }

    /**
     * Encuentra la primera vocal en una palabra.
     *
     * @param word La palabra donde buscar
     * @return La primera vocal encontrada o "X" si no hay coincidencias
     */
    private static char findVowel(String word) {
        return findChar(VOWELS, word);
    }

    /**
     * Encuentra la primera consonante en una palabra.
     *
     * @param word La palabra donde buscar
     * @return La primera consonante encontrada o "X" si no hay coincidencias
     */
    private static char findConsonant(String word) {
        return findChar(CONSONANTS, word);
    }

    /**
     * @return El primer carácter de la palabra.
     */
    public char getChar() {
        return character;
    }

    /**
     * @return La primera vocal interna de la palabra.
     */
    public char getVowel() {
        return vowel;
    }

    /**
     * @return La primera consonante interna de la palabra.
     */
    public char getConsonant() {
        return consonant;
    }
}
