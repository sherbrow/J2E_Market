/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sherbrow
 */
public abstract class MvcController {
    
	public ActionResult call(String actionName,HttpServletRequest request, HttpServletResponse response, Object... parameters) {
            ActionResult actionResult = null;
            try{
                    String httpMethod = request.getMethod();
                    Method actionMethod;
                    try{
                        actionMethod = this.getClass().getDeclaredMethod(httpMethod.toLowerCase()+"_"+actionName);
                    }
                    catch(NoSuchMethodException e) {
                        actionMethod = this.getClass().getDeclaredMethod(actionName);
                    }
                    actionResult = (ActionResult)actionMethod.invoke(this, parameters);
            }
            catch (NoSuchMethodException e) {
                return new ActionResult("404 Not Found (action): "+actionName, ActionResult.TYPE_STRING + ActionResult.HTTP_404);
            }
            catch (Exception e) {
                    e.printStackTrace();
                    actionResult.setError(e);
                    actionResult.addType(ActionResult.TYPE_ERROR);
                    return actionResult;
            }

            return actionResult;
	}
}
