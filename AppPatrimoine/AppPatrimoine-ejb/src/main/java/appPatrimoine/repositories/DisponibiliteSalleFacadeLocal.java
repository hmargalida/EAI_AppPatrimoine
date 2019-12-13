/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.repositories;

import appPatrimoine.entities.DisponibiliteSalle;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sylvia
 */
@Local
public interface DisponibiliteSalleFacadeLocal {
    void create(DisponibiliteSalle disponibiliteSalle);

    void edit(DisponibiliteSalle disponibiliteSalle);

    void remove(DisponibiliteSalle disponibiliteSalle);

    DisponibiliteSalle find(Object id);

    List<DisponibiliteSalle> findAll();

    List<DisponibiliteSalle> findRange(int[] range);

    int count();
}
