/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appPatrimoine.JMS;

import appPatrimoine.export.DisponibiliteSalleExport;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author Sylvia
 */
/**
 * Envoie du catalogue à l'application Cliente
 * @author Sylvia
 */
@Stateless
public class SendListeSalleDispos implements SendListeSalleDisposLocal {

    /**
     * Nom du Topic recherché.
     */
    @Resource(mappedName = "ListeSallesDispos")
    private Topic listeSallesDisposTopic;
    
    private Gson gson;

    /**
     * contexte JMS. Injection auto par serveur d'appli.
     */
    @Inject
    @JMSConnectionFactory("ConnectionFactory")
    private JMSContext context;

    public SendListeSalleDispos() {
        gson=new Gson();
    }


    @Override
    public void sendListeSalleDispos(DisponibiliteSalleExport salleDispos, String niveau) {
        try {
            JMSProducer producer = context.createProducer();

            TextMessage mess = context.createTextMessage();
            mess.setText(this.gson.toJson(salleDispos));
            mess.setJMSType("ListeSallesDispos");
            context.createProducer().send(listeSallesDisposTopic, mess);
            System.out.println(salleDispos + " envoyé.");

        } catch (JMSException ex) {
            Logger.getLogger(SendListeSalleDispos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
