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
 * @author Heloise
 */
public class SallesTableExport implements Serializable {
    
    List<SalleExport> salles;

    public SallesTableExport() {
        this.salles = new ArrayList<>();
    }

    public List<SalleExport> getSalles() {
        return salles;
    }

    public void setSalles(List<SalleExport> salles) {
        this.salles = salles;
    }
    
    public void addSalle(SalleExport salle) {
        this.salles.add(salle);
    }
}
