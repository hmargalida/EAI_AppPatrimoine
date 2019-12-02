/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Sylvia
 */
@Entity
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idSalle;

    public Long getIdSalle() {
        return idSalle;
    }
    
    /**
     * L'énumération contenant les équipements d'une salle.
     */
    public enum Equipement{
        
    }
    public Equipement equipement;
    
    /**
     * Liste des équipements nécessaires dans la salle pour la formation.
     */
    private List<Equipement> equipementsNecessaires;
    
    public enum Statut{
        INDISPONIBLE,AFFECTEE,PRESSENTIE
    }
    public Statut statut;

    public Equipement getEquipement() {
        return equipement;
    }

    public List<Equipement> getEquipementsNecessaires() {
        return equipementsNecessaires;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public void setEquipementsNecessaires(List<Equipement> equipementsNecessaires) {
        this.equipementsNecessaires = equipementsNecessaires;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public void setPlanningSalle(Map<Date, Statut> planningSalle) {
        this.planningSalle = planningSalle;
    }

    public Statut getStatut() {
        return statut;
    }

    public Map<Date, Statut> getPlanningSalle() {
        return planningSalle;
    }
    
    private Map<Date,Statut> planningSalle;

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSalle != null ? idSalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salle)) {
            return false;
        }
        Salle other = (Salle) object;
        if ((this.idSalle == null && other.idSalle != null) || (this.idSalle != null && !this.idSalle.equals(other.idSalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "appPatrimoine.entities.Salle[ idSalle=" + idSalle + " ]";
    }
    
}
