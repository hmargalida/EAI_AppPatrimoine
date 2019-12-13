/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.export;

import appPatrimoine.enumerations.StatutSalle;
import java.io.Serializable;

/**
 *
 * @author Sylvia
 */
public class DisponibiliteSalleExport implements Serializable{
    private int numSemaine;
    
    private StatutSalle statutSalle;
    
    public DisponibiliteSalleExport() {
    }
    
    public DisponibiliteSalleExport(int numSem, StatutSalle statut) {
        this.numSemaine = numSem;
        this.statutSalle = statut;
    }

    public int getNumSemaine() {
        return numSemaine;
    }

    public void setNumSemaine(int numSemaine) {
        this.numSemaine = numSemaine;
    }

    public StatutSalle getStatutSalle() {
        return statutSalle;
    }

    public void setStatutSalle(StatutSalle statutSalle) {
        this.statutSalle = statutSalle;
    }
}
