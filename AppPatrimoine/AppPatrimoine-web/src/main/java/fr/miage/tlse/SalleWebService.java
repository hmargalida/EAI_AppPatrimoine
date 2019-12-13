/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.tlse;

import appPatrimoine.entities.Salle;
import appPatrimoine.exception.MauvaisStatutPrecedentException;
import appPatrimoine.exception.NumeroSemaineIncorrect;
import appPatrimoine.exception.SalleNotFoundException;
import appPatrimoine.export.DisponibilitesExport;
import appPatrimoine.export.SalleExport;
import com.google.gson.Gson;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Sylvia
 */
@Path("/salle")
@RequestScoped
public class SalleWebService {

    appPatrimoine.business.GestionPatrimoineLocal gestionPatrimoine = lookupGestionPatrimoineLocal();

    
    @Context
    private UriInfo context;
    
    private Gson gson = new Gson();

    /**
     * Creates a new instance of FormationWebService
     */
    public SalleWebService() {
    }

    private appPatrimoine.business.GestionPatrimoineLocal lookupGestionPatrimoineLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (appPatrimoine.business.GestionPatrimoineLocal) c.lookup(""
                    + "java:global/AppPatrimoine-ear/AppPatrimoine-ejb-1.0-SNAPSHOT/GestionPatrimoine!appPatrimoine.business.GestionPatrimoineLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * Retrieves representation of an instance of fr.miage.tlse.FormationWebService
     * @return an instance of java.lang.String
     */
    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }*/

    /**
     * PUT method for updating or creating an instance of FormationWebService
     * @param content representation for the resource
     */
    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }*/

    @Path("ajoutSalle")
    @POST()
    public String ajoutSalle(@QueryParam("id") Long id) {
        gestionPatrimoine.creerSalleSiInnexistante(id);
        return "Salle ajoutée";
    }
    
    @Path("{idSalle}/ajoutEquipements")
    @Produces(MediaType.APPLICATION_JSON)
    @POST()
    public String ajouterEquipementsALaSalle(@PathParam("idSalle") long idSalle, @QueryParam("newEquipement") String equip) throws SalleNotFoundException {
        try{    
            Salle s = gestionPatrimoine.ajouterEquipementsALaSalle(idSalle, equip);
            return "Equipement ajouté";
        } catch(SalleNotFoundException ex){
            return "Erreur lors de l'ajout d'équipement à la salle";
        }
    }
    
    @Path("{idSalle}/majStatutSalle")
    @Produces(MediaType.APPLICATION_JSON)
    @POST()
    public String majStatutSalle(@PathParam("idSalle") long idSalle, @QueryParam("numSem") int numSem, @QueryParam("statut") String statut) {
        try {
            Salle s = gestionPatrimoine.majStatutSalle(idSalle, statut, numSem);
            return "Planning de la salle mis à jour ! ";
        } catch (SalleNotFoundException | NumeroSemaineIncorrect | MauvaisStatutPrecedentException ex) {
            return "Erreur lors de la mise à jour : " + ex.getMessage();
        }
    }
    
    @Path("editerListeSallesDispo")
    @Produces(MediaType.APPLICATION_JSON)
    @GET()
    public String editerListeSalleDispo() {
        List<DisponibilitesExport> disponibilites = gestionPatrimoine.editerListeSallesDispos();
        String jsonString = gson.toJson(disponibilites);
        return jsonString;
    }
    
    @Path("affecterSalle")
    @Produces(MediaType.APPLICATION_JSON)
    @POST()
    public String affecterSalle() {
        try {
            SalleExport salle = gestionPatrimoine.affecterSalle();
            String jsonString = gson.toJson(salle);
            return jsonString;
        } catch (SalleNotFoundException | NumeroSemaineIncorrect | MauvaisStatutPrecedentException ex) {
            return "Erreur lors de l'affectation : " + ex.getMessage();
        }
    }
    
    @Path("transmettreListeSalles")
    @POST()
    public String transmettreListeSalles() {
        return this.gestionPatrimoine.transmettreListeSalles();
    }
}
