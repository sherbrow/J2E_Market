/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.util.HashMap;
import web.ActionResult;
import web.MvcController;

/**
 *
 * @author Sherbrow
 */
public class CatalogueController extends MvcController {
    
    public ActionResult index() {
        HashMap<String, Object> viewBag = new HashMap<String, Object>();
        
        viewBag.put("message", "Hello world from the Catalogue Controller !");
        
        ActionResult actionResult = new ActionResult("catalogue/index", viewBag);
        return actionResult;
    }
    
}
