/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.business;

import appPatrimoine.entities.DisponibiliteSalle;
import appPatrimoine.entities.Salle;
import appPatrimoine.enumerations.Equipement;
import appPatrimoine.enumerations.StatutSalle;
import appPatrimoine.enumerations.StatutSession;
import appPatrimoine.exception.FormationInexistanteException;
import appPatrimoine.exception.FormationNotFoundException;
import appPatrimoine.exception.MauvaisStatutPrecedentException;
import appPatrimoine.exception.NumeroSemaineIncorrect;
import appPatrimoine.exception.SalleNonEquipeesOuDispoException;
import appPatrimoine.exception.SalleNotFoundException;
import appPatrimoine.exception.SessionNotFoundException;
import appPatrimoine.export.DisponibiliteSalleExport;
import appPatrimoine.export.DisponibilitesExport;
import appPatrimoine.export.FormationExport;
import appPatrimoine.export.SalleExport;
import appPatrimoine.export.SallesTableExport;
import appPatrimoine.export.SessionExport;
import appPatrimoine.repositories.DisponibiliteSalleFacadeLocal;
import appPatrimoine.repositories.SalleFacadeLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sylvia
 */
@Stateless
public class GestionPatrimoine implements GestionPatrimoineLocal {

    @EJB
    private SalleFacadeLocal salleFacade;

    @EJB
    private DisponibiliteSalleFacadeLocal disponibilitefacade;
    
    /**
     * Création d'une salle si elle n'existe pas en base
     *
     * @return void
     */
    @Override
    public Salle creerSalleSiInnexistante(long idSalle) {
        List<Salle> listSalle = salleFacade.findAll();
        int i = 0;
        while (i < listSalle.size()) {
            // si elle existe déjà, on retourne le client via SalleExport
            if (listSalle.get(i).getIdSalle().equals(idSalle)) {
                return listSalle.get(i);
            }
            i++;
        }
        Salle nouvelleSalle = new Salle(idSalle);
        this.salleFacade.create(nouvelleSalle);

        int numSemaine = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        int nombreSemaineTotal = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);
        for (int j = numSemaine; j <= nombreSemaineTotal; j++) {
            DisponibiliteSalle dispo = new DisponibiliteSalle(j, StatutSalle.DISPONIBLE);
            dispo.setSalle(nouvelleSalle);
            this.disponibilitefacade.create(dispo);
            nouvelleSalle.addDisponibilite(dispo);
        }
        this.salleFacade.edit(nouvelleSalle);
        return nouvelleSalle;
    }
    
    /**
     * Ajouter une liste d'équipements à une Salle
     *
     * @param idSalle identifiant de la salle
     * @param equipements liste d'Equipements
     * @return Salle
     * @throws SalleNotFoundException
     */
    @Override
    public Salle ajouterEquipementsALaSalle(long idSalle, String equipements) throws SalleNotFoundException {
        //recherche de la salle passée en paramètre
        final Salle s = this.salleFacade.find(idSalle);
        if (s == null) {
            throw new SalleNotFoundException("Salle inconnue");
        }

        // pour chaque équipement
        Equipement newEquipement = Equipement.valueOf(equipements);
        if (!s.possedeEquipement(newEquipement)) {
            s.addEquipement(newEquipement);
            this.salleFacade.edit(s);
        }
        return s;
    }
    

    @Override
    public List<DisponibilitesExport> editerListeSallesDispos() {
        // JMS : Réception de la liste de toutes les salles et de la formation en question
        List<Salle> toutesLesSalles = salleFacade.findAll();
        FormationExport formation = new FormationExport(0);

        DisponibilitesExport disponibilites;
        List<DisponibilitesExport> listeDesDisponibilites = new ArrayList<>();
        for (Salle salle : toutesLesSalles) {
            List<Integer> numsSemaine = new ArrayList<>();
            for (DisponibiliteSalle disponibiliteDeLaSalle : salle.getDisponibiliteSalle()) {
                if (disponibiliteDeLaSalle.getStatutSalle().equals(StatutSalle.DISPONIBLE)) {
                    numsSemaine.add(disponibiliteDeLaSalle.getNumSemaine());
                }
            }
            disponibilites = new DisponibilitesExport(formation.getIdFormation(), salle.getIdSalle());
            disponibilites.setListeSemainesDispo(numsSemaine);
            listeDesDisponibilites.add(disponibilites);
        }
        return listeDesDisponibilites;    
    }

    @Override
    public SalleExport affecterSalle() throws SalleNotFoundException, NumeroSemaineIncorrect, MauvaisStatutPrecedentException {
        // récupération du JMSProvider de la Session Plannifiée/Annulée
        SessionExport sessionAnnulee = new SessionExport(1, 151, StatutSession.ANNULEE, 50);
        StatutSession statutSession = sessionAnnulee.getStatut();
        /*SessionExport sessionPlanifiee = new SessionExport(1, 151, StatutSession.PLANIFIEE, 50);
        StatutSession statutSessionPlanifiee = sessionPlanifiee.getStatut();
        SessionExport sessionEnProjet = new SessionExport(1, 151, StatutSession.EN_PROJET, 50);
        StatutSession statutSession = sessionEnProjet.getStatut();*/
        Salle s = salleFacade.find(sessionAnnulee.getIdSalle());
        if(s == null) {
            throw new SalleNotFoundException("Salle inconnue");
        }
        
        switch(statutSession) {
            case EN_PROJET:
                // on passe la salle à pressentie
                if(!s.getDisponibilitesDeLaSalle().contains(sessionAnnulee.getNumSemaine())) {
                    throw new MauvaisStatutPrecedentException("La salle n'est pas disponible à cette période");
                }
                this.majStatutSalle(sessionAnnulee.getIdSalle(), StatutSalle.PRESSENTIE.getId(), sessionAnnulee.getNumSemaine());
                break;
            case PLANIFIEE:
                // on passe la salle à affectée
                // on peut la passer à affectée si elle est disponible avant 
                this.majStatutSalle(sessionAnnulee.getIdSalle(), StatutSalle.AFFECTEE.getId(), sessionAnnulee.getNumSemaine());
                break;
            case ANNULEE:
                // on passe la salle à dispo
                // on peut la passer à dispo si elle était affectée ou pressentie
                this.majStatutSalle(sessionAnnulee.getIdSalle(), StatutSalle.DISPONIBLE.getId(), sessionAnnulee.getNumSemaine());
                break;
            default:
                break;
                
        }
        // RETOUR
        SalleExport retour = new SalleExport(s.getIdSalle());
        List<DisponibiliteSalle> planning = s.getDisponibiliteSalle();
        List<DisponibiliteSalleExport> exportPlanning = new ArrayList<>();
        for(DisponibiliteSalle disp : planning) {
            DisponibiliteSalleExport export = new DisponibiliteSalleExport(disp.getNumSemaine(), disp.getStatutSalle());
            exportPlanning.add(export);
        }
        retour.setDisponibiliteSalle(exportPlanning);
        return retour;
        }

    /**
     * Mise à jour du statut d'une salle à une date donnée
     *
     * @param idSalle
     * @param statut
     * @param numSem
     * @return
     * @throws SalleNotFoundException
     * @throws appPatrimoine.exception.NumeroSemaineIncorrect
     */
    @Override
    public Salle majStatutSalle(long idSalle, String statut, int numSem) throws SalleNotFoundException, NumeroSemaineIncorrect, MauvaisStatutPrecedentException {
        final Salle s = this.salleFacade.find(idSalle);
        if (s == null) {
            throw new SalleNotFoundException("Salle inconnue");
        }
        int nombreSemaineTotal = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);
        int numSemaineActuelle = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        if(numSem > nombreSemaineTotal || numSem <= numSemaineActuelle) {
            throw new NumeroSemaineIncorrect("Le numéro de la semaine renseigné est incorrect");
        }
        List<DisponibiliteSalle> planning = s.getDisponibiliteSalle();
        StatutSalle newStatut = StatutSalle.valueOf(statut);
        for (DisponibiliteSalle dispo : planning) {
            if (dispo.getNumSemaine() == numSem) {
                dispo.setStatutSalle(newStatut);
            }
        }
        this.salleFacade.edit(s);
        return s;
    }

    @Override
    public String transmettreListeSalles() {
        SallesTableExport export = new SallesTableExport();
        List<Salle> salles = this.salleFacade.findAll();
        for(Salle s : salles) {
            SalleExport se = new SalleExport(s.getIdSalle());
            se.setListeEquipements(s.getEquipementsNecessaires());
            List<DisponibiliteSalle> planning = s.getDisponibiliteSalle();
            List<DisponibiliteSalleExport> exportPlanning = new ArrayList<>();
            for (DisponibiliteSalle disp : planning) {
                DisponibiliteSalleExport exportDispo = new DisponibiliteSalleExport(disp.getNumSemaine(), disp.getStatutSalle());
                exportPlanning.add(exportDispo);
            }
            se.setDisponibiliteSalle(exportPlanning);
            export.addSalle(se);
        }
        //this.sendSalles.sendSalles(export);
        return "La liste a été envoyée";
    }
}
