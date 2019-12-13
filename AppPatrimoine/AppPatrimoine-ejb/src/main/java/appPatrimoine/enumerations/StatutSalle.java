/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.enumerations;

/**
 *
 * @author Sylvia
 */
public enum StatutSalle {
    INDISPONIBLE("Indisponible"), AFFECTEE("Affectee"), PRESSENTIE("Pressentie"), DISPONIBLE("Disponible");

    private String libelle;

    StatutSalle() {

    }

    StatutSalle(String libelle) {
        this.libelle = libelle;
    }

    public String getId() {
        return name();
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
