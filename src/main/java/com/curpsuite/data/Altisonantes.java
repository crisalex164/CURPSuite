package com.curpsuite.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Altisonantes {

    private static final Map<String, String[]> ALTISONANTES;

    static {
        Map<String, String[]> map = new HashMap<>();

        map.put("BXCA", new String[]{"A"});
        map.put("BXKA", new String[]{"A"});
        map.put("BXEI", new String[]{"U"});
        map.put("BXEY", new String[]{"U"});
        map.put("CXCA", new String[]{"A"});
        map.put("CXCO", new String[]{"A"});
        map.put("CXGA", new String[]{"A"});
        map.put("CXGO", new String[]{"A"});
        map.put("CXKA", new String[]{"A"});
        map.put("CXKO", new String[]{"A"});
        map.put("CXGE", new String[]{"O"});
        map.put("CXGI", new String[]{"O"});
        map.put("CXJA", new String[]{"O"});
        map.put("CXJE", new String[]{"O"});
        map.put("CXJI", new String[]{"O"});
        map.put("CXJO", new String[]{"O"});
        map.put("CXLA", new String[]{"O"});
        map.put("CXLO", new String[]{"U"});
        map.put("FXLO", new String[]{"A"});
        map.put("FXTO", new String[]{"E"});
        map.put("GXTA", new String[]{"E"});
        map.put("GXEI", new String[]{"U"});
        map.put("GXEY", new String[]{"U"});
        map.put("JXTA", new String[]{"E"});
        map.put("JXTO", new String[]{"O"});
        map.put("KXCA", new String[]{"A"});
        map.put("KXCO", new String[]{"A"});
        map.put("KXGA", new String[]{"A"});
        map.put("KXGO", new String[]{"A"});
        map.put("KXKA", new String[]{"A"});
        map.put("KXKO", new String[]{"A"});
        map.put("KXGE", new String[]{"O"});
        map.put("KXGI", new String[]{"O"});
        map.put("KXJA", new String[]{"O"});
        map.put("KXJE", new String[]{"O"});
        map.put("KXJI", new String[]{"O"});
        map.put("KXJO", new String[]{"O"});
        map.put("KXLA", new String[]{"O"});
        map.put("KXLO", new String[]{"U"});
        map.put("LXLO", new String[]{"I"});
        map.put("LXKA", new String[]{"O"});
        map.put("LXKO", new String[]{"O"});
        map.put("MXME", new String[]{"A"});
        map.put("MXMO", new String[]{"A"});
        map.put("MXAR", new String[]{"E", "I"});
        map.put("MXAS", new String[]{"E"});
        map.put("MXON", new String[]{"E", "I"});
        map.put("MXCO", new String[]{"O"});
        map.put("MXKO", new String[]{"O"});
        map.put("MXLA", new String[]{"U"});
        map.put("MXLO", new String[]{"U"});
        map.put("NXCA", new String[]{"A"});
        map.put("NXCO", new String[]{"A"});
        map.put("PXDA", new String[]{"E"});
        map.put("PXDO", new String[]{"E"});
        map.put("PXNE", new String[]{"E"});
        map.put("PXPI", new String[]{"I"});
        map.put("PXTO", new String[]{"I", "U"});
        map.put("PXPO", new String[]{"O"});
        map.put("PXTA", new String[]{"U"});
        map.put("QXLO", new String[]{"U"});
        map.put("RXTA", new String[]{"A"});
        map.put("RXBA", new String[]{"O"});
        map.put("RXBE", new String[]{"O"});
        map.put("RXBO", new String[]{"O"});
        map.put("RXIN", new String[]{"U"});
        map.put("SXNO", new String[]{"E"});
        map.put("TXTA", new String[]{"E"});
        map.put("VXCA", new String[]{"A"});
        map.put("VXGA", new String[]{"A"});
        map.put("VXGO", new String[]{"A"});
        map.put("VXKA", new String[]{"A"});
        map.put("VXEI", new String[]{"U"});
        map.put("VXEY", new String[]{"U"});
        map.put("WXEI", new String[]{"U"});
        map.put("WXEY", new String[]{"U"});

        ALTISONANTES = Collections.unmodifiableMap(map);
    }

    /**
     * Obtiene el mapa de palabras altisonantes censuradas.
     *
     * @return Un mapa no modificable que contiene las palabras altisonantes
     */
    public static Map<String, String[]> getAltisonantes() {
        return ALTISONANTES;
    }
}
