/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.JMS;

import appPatrimoine.export.DisponibiliteSalleExport;
import javax.ejb.Local;

/**
 *
 * @author Sylvia
 */
@Local
public interface SendListeSalleDisposLocal {
    public void sendListeSalleDispos(DisponibiliteSalleExport salleDispos, String niveau);
}
