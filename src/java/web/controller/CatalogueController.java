/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Catalogue;
import web.ActionResult;
import web.MvcController;

/**
 *
 * @author Sherbrow
 */
public class CatalogueController extends MvcController {
    
    public ActionResult index() {
        HashMap<String, Object> viewBag = new HashMap<String, Object>();

        EntityManager em = this.createEntityManager();
        
        Query q = em.createQuery("select object(o) from Catalogue as o");
        viewBag.put("catalogues", q.getResultList());
        
        closeEntityManager();
        
        ActionResult actionResult = new ActionResult("catalogue/index", viewBag);
        return actionResult;
    }
    
    public ActionResult add() {
        ActionResult actionResult = new ActionResult("catalogue/add");
        return actionResult;
    }
    public ActionResult post_add() {
        HashMap<String, Object> viewBag = new HashMap<String, Object>();
        
        String name = getParameter("name");
        String description = getParameter("description");
        
        Catalogue catalogue = new Catalogue();
        catalogue.setName(name);
        catalogue.setDescription(description);
        
        if(catalogue.isValid()) {
            EntityManager em = this.createEntityManager();
            em.getTransaction().begin();
            em.persist(catalogue);
            em.getTransaction().commit();
            closeEntityManager();
            return new ActionResult("catalogue", ActionResult.TYPE_REDIRECT);
        }
        
        viewBag.put("error", "Invalid");
        viewBag.put("name", name);
        viewBag.put("description", description);
        
        ActionResult actionResult = new ActionResult("catalogue/add", viewBag);
        return actionResult;
    }
    
}
