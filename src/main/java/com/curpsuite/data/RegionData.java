package com.curpsuite.data;

public class RegionData {
    private final String name;
    private final String iso;

    /**
     * Crea un nuevo objeto RegionData.
     *
     * @param name Nombre de la entidad federativa
     * @param iso Código ISO de la entidad o null si es extranjero
     */
    public RegionData(String name, String iso) {
        this.name = name;
        this.iso = iso;
    }

    /**
     * @return Nombre de la entidad federativa
     */
    public String getName() {
        return name;
    }

    /**
     * @return Código ISO de la entidad o null si es extranjero
     */
    public String getIso() {
        return iso;
    }

    @Override
    public String toString() {
        return name;
    }
}
