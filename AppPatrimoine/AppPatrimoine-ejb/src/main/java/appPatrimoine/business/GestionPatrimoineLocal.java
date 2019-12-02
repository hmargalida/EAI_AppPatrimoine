/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.business;

import appPatrimoine.entities.Salle.Statut;
import appPatrimoine.exception.FormationInexistanteException;
import appPatrimoine.exception.FormationNotFoundException;
import appPatrimoine.exception.SalleNonEquipeesOuDispoException;
import appPatrimoine.exception.SalleNotFoundException;
import appPatrimoine.exception.SessionNotFoundException;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Sylvia
 */
@Local
public interface GestionPatrimoineLocal {
    void libererSalle(Date date, int duree);
    
    boolean getSallesDispos(long idFormation, Date date) throws FormationInexistanteException;
    
    void majStatutSalle(Statut statut, Date date);
    
    void affecterSalle(long codeFormation, long codeSession, long codeSalle, Date date) throws SalleNonEquipeesOuDispoException, SalleNotFoundException, SessionNotFoundException, FormationNotFoundException;
    
}
