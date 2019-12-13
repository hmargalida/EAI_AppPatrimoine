/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.export;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sylvia
 */
public class DisponibilitesExport implements Serializable{
    private long idFormation;
    
    private long idSalle;
    
    private List<Integer> listeSemainesDispo;
    
    public DisponibilitesExport(long idFormation, long idSalle) {
        this.idSalle = idSalle;
        this.idFormation = idFormation;
        this.listeSemainesDispo = new ArrayList<>();
    }

    public long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(long idFormation) {
        this.idFormation = idFormation;
    }

    public long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(long idSalle) {
        this.idSalle = idSalle;
    }

    public List<Integer> getListeSemainesDispo() {
        return listeSemainesDispo;
    }

    public void setListeSemainesDispo(List<Integer> listeSemainesDispo) {
        this.listeSemainesDispo = listeSemainesDispo;
    }
}
