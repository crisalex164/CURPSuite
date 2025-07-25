package com.curpsuite.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Estados {
    private static final Map<String, RegionData> ESTADOS;

    static {
        Map<String, RegionData> map = new HashMap<>();

        map.put("AS", new RegionData("Aguascalientes", "MX-AGU"));
        map.put("BC", new RegionData("Baja California", "MX-BCN"));
        map.put("BS", new RegionData("Baja California Sur", "MX-BCS"));
        map.put("CC", new RegionData("Campeche", "MX-CAM"));
        map.put("CL", new RegionData("Coahuila de Zaragoza", "MX-COA"));
        map.put("CM", new RegionData("Colima", "MX-COL"));
        map.put("CS", new RegionData("Chiapas", "MX-CHP"));
        map.put("CH", new RegionData("Chihuahua", "MX-CHH"));
        map.put("DF", new RegionData("Ciudad de México", "MX-CMX"));
        map.put("DG", new RegionData("Durango", "MX-DUR"));
        map.put("GT", new RegionData("Guanajuato", "MX-GUA"));
        map.put("GR", new RegionData("Guerrero", "MX-GRO"));
        map.put("HG", new RegionData("Hidalgo", "MX-HID"));
        map.put("JC", new RegionData("Jalisco", "MX-JAL"));
        map.put("MC", new RegionData("México", "MX-MEX"));
        map.put("MN", new RegionData("Michoacán de Ocampo", "MX-MIC"));
        map.put("MS", new RegionData("Morelos", "MX-MOR"));
        map.put("NT", new RegionData("Nayarit", "MX-NAY"));
        map.put("NL", new RegionData("Nuevo León", "MX-NLE"));
        map.put("OC", new RegionData("Oaxaca", "MX-OAX"));
        map.put("PL", new RegionData("Puebla", "MX-PUE"));
        map.put("QT", new RegionData("Querétaro", "MX-QUE"));
        map.put("QR", new RegionData("Quintana Roo", "MX-ROO"));
        map.put("SP", new RegionData("San Luis Potosí", "MX-SLP"));
        map.put("SL", new RegionData("Sinaloa", "MX-SIN"));
        map.put("SR", new RegionData("Sonora", "MX-SON"));
        map.put("TC", new RegionData("Tabasco", "MX-TAB"));
        map.put("TS", new RegionData("Tamaulipas", "MX-TAM"));
        map.put("TL", new RegionData("Tlaxcala", "MX-TLA"));
        map.put("VZ", new RegionData("Veracruz de Ignacio de la Llave", "MX-VER"));
        map.put("YN", new RegionData("Yucatán", "MX-YUC"));
        map.put("ZS", new RegionData("Zacatecas", "MX-ZAC"));
        map.put("NE", new RegionData("Extranjero", null));

        ESTADOS = Collections.unmodifiableMap(map);
    }

    /**
     * Obtiene el mapa de estados.
     *
     * @return Un mapa no modificable que contiene los estados de México
     */
    public static Map<String, RegionData> getEstados() {
        return ESTADOS;
    }
}
