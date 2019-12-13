/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.repositories;

import appPatrimoine.entities.DisponibiliteSalle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sylvia
 */
@Stateless
public class DisponibiliteSalleFacade extends AbstractFacade<DisponibiliteSalle> implements DisponibiliteSalleFacadeLocal {

    @PersistenceContext(unitName = "AppPatrimoinePersistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisponibiliteSalleFacade() {
        super(DisponibiliteSalle.class);
    }

}
