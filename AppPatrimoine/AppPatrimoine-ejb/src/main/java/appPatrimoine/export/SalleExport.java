/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.export;

import appPatrimoine.enumerations.Equipement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sylvia
 */
public class SalleExport implements Serializable{
    
    private Long idSalle;

    List<Equipement> listeEquipements;
    
    List<DisponibiliteSalleExport> disponibiliteSalle;
    
    /**
     * Constructeur d'un objet Client
     */
    protected SalleExport() {
    }

    /**
     * Constructeur d'un objet Salle
     * @param idSalle l'identifiant de la salle
     */
    public SalleExport(long idSalle) {
        this.idSalle = idSalle;
        this.listeEquipements = new ArrayList<>();
        this.disponibiliteSalle = new ArrayList<>();
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public List<Equipement> getListeEquipements() {
        return listeEquipements;
    }

    public void setListeEquipements(List<Equipement> listeEquipements) {
        this.listeEquipements = listeEquipements;
    }

    public List<DisponibiliteSalleExport> getDisponibiliteSalle() {
        return disponibiliteSalle;
    }

    public void setDisponibiliteSalle(List<DisponibiliteSalleExport> disponibiliteSalle) {
        this.disponibiliteSalle = disponibiliteSalle;
    }
}
