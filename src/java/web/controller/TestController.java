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
        ActionResult actionResult = new ActionResult("index");
        return actionResult;
    }
    
    public ActionResult hello() {
        ActionResult actionResult = new ActionResult("Hello world from the Test Controller !", ActionResult.TYPE_STRING);
        return actionResult;
    }
    
}
