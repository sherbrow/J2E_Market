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
public class TestController extends MvcController {
    
    public ActionResult index() {
        ActionResult actionResult = new ActionResult("Hello world from the Test Controller !");
        return actionResult;
    }
    
}
