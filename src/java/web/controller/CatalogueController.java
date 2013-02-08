/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import web.ActionResult;
import web.MvcController;

/**
 *
 * @author Sherbrow
 */
public class CatalogueController extends MvcController {
    
    public ActionResult add() {
        
        ActionResult actionResult = new ActionResult("Hello world from the Catalogue Controller !");
        return actionResult;
    }
    
}
