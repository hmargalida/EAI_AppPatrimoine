/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.business;

import appPatrimoine.entities.Salle;
import appPatrimoine.exception.FormationInexistanteException;
import appPatrimoine.exception.FormationNotFoundException;
import appPatrimoine.exception.SalleNonEquipeesOuDispoException;
import appPatrimoine.exception.SalleNotFoundException;
import appPatrimoine.exception.SessionNotFoundException;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author Sylvia
 */
@Stateless
public class GestionPatrimoine implements GestionPatrimoineLocal {

    @Override
    public void libererSalle(Date date, int duree) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getSallesDispos(long idFormation, Date date) throws FormationInexistanteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void majStatutSalle(Salle.Statut statut, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void affecterSalle(long codeFormation, long codeSession, long codeSalle, Date date) throws SalleNonEquipeesOuDispoException, SalleNotFoundException, SessionNotFoundException, FormationNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
