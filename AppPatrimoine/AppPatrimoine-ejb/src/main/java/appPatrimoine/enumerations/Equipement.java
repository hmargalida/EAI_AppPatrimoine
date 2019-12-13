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
public enum Equipement {
    VIDEOPROJECTEUR("VideoProjecteur"), TABLES("Tables"), CHAISES("Chaises"), TABLEAU("Tableau");
    
    private String libelle;
    
    Equipement() {
        
    }
    
    Equipement(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
