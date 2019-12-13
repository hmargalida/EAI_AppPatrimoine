/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.business;

import appPatrimoine.entities.Salle;
import appPatrimoine.exception.SalleNotFoundException;
import appPatrimoine.exception.NumeroSemaineIncorrect;
import appPatrimoine.exception.MauvaisStatutPrecedentException;
import appPatrimoine.export.DisponibilitesExport;
import appPatrimoine.export.SalleExport;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sylvia
 */
@Local
public interface GestionPatrimoineLocal {
       
    /**
     * Création d'une salle si elle n'existe pas en base
     * @param idSalle identifiant à une salle
     * @return void 
     */
    Salle creerSalleSiInnexistante(long idSalle);
    
    /**
     * Ajouter une liste d'équipements à une salle
     * @param idSalle identifiant d'une salle
     * @param equipements liste d'Equipements
     * @return 
     * @throws fr.miage.tlse.apprh.exception.SalleNotFoundException
     */    
    Salle ajouterEquipementsALaSalle(long idSalle, String equipements) throws SalleNotFoundException;
    
    /**
     * Edition de la liste des salles disponibles
     * A partir de la liste de salles équipées fourni par AppTechniCom via le JMS
     * @return Map<Integer, List>
     */
    List<DisponibilitesExport> editerListeSallesDispos();
    
    /**
     * Affecter une salle à une session
     * @return 
     * @throws fr.miage.tlse.apprh.exception.SalleNotFoundException
     * @throws fr.miage.tlse.apprh.exception.NumeroSemaineIncorrect
     * @throws fr.miage.tlse.apprh.exception.MauvaisStatutPrecedentException
     */
    SalleExport affecterSalle() throws SalleNotFoundException, NumeroSemaineIncorrect, MauvaisStatutPrecedentException;
    
    /**
     * Mettre à jour le statut d'une salle
     * @param idSalle
     * @param statut nouveau statut de la salle
     * @param numSem
     * @return 
     * @throws fr.miage.tlse.apprh.exception.SalleNotFoundException
     * @throws fr.miage.tlse.apprh.exception.NumeroSemaineIncorrect
     */
    Salle majStatutSalle(long idSalle, String statut, int numSem) throws SalleNotFoundException, NumeroSemaineIncorrect, MauvaisStatutPrecedentException;
    
    String transmettreListeSalles();
    
}
