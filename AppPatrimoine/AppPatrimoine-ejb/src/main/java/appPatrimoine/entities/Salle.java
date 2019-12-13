/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.entities;

import appPatrimoine.enumerations.Equipement;
import appPatrimoine.enumerations.StatutSalle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @ElementCollection
    @OneToMany
    List<Equipement> listeEquipements;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salle")
    List<DisponibiliteSalle> disponibiliteSalle;

    /**
     * Constructeur sans paramètre
     */
    protected Salle() {
    }

    /**
     * Constructeur d'un objet Salle
     *
     * @param idSalle identifiant de la salle
     */
    public Salle(long idSalle) {
        this.idSalle = idSalle;
        this.listeEquipements = new ArrayList<>();
        this.disponibiliteSalle = new ArrayList<>();
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public List<Equipement> getEquipementsNecessaires() {
        return listeEquipements;
    }

    public void setEquipementsNecessaires(List<Equipement> equipementsNecessaires) {
        this.listeEquipements = equipementsNecessaires;
    }

    public List<DisponibiliteSalle> getDisponibiliteSalle() {
        return disponibiliteSalle;
    }

    public void setDisponibiliteSalle(List<DisponibiliteSalle> disponibiliteSalle) {
        this.disponibiliteSalle = disponibiliteSalle;
    }

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Salle other = (Salle) obj;
        return Objects.equals(this.idSalle, other.idSalle);
    }
    
    public boolean possedeEquipement(Equipement e) {
        return this.listeEquipements.contains(e);
    }
    
    public void addEquipement(Equipement e) {
        this.listeEquipements.add(e);
    }
    
    public void addDisponibilite(DisponibiliteSalle dispo) {
        this.disponibiliteSalle.add(dispo);
    }
    
    public List<Integer> getDisponibilitesDeLaSalle() {
        List<Integer> listDisponibiliteSalle = new ArrayList<>();
        for(DisponibiliteSalle dispo : this.disponibiliteSalle) {
            if(dispo.getStatutSalle().equals(StatutSalle.DISPONIBLE)) {
                listDisponibiliteSalle.add(dispo.getNumSemaine());
            }
        }
        return listDisponibiliteSalle;
    }
    
    public boolean isAnneeBisextile() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    @Override
    public String toString() {
        return "appPatrimoine.entities.Salle[ idSalle=" + idSalle + " ]";
    }

}
