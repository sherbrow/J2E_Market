/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sherbrow
 */
public abstract class MvcController {

    
        private HttpServletRequest _request;
        private HttpServletResponse _response;
        private EntityManagerFactory _emf;
        private EntityManager _em;
    
	public ActionResult call(String actionName,HttpServletRequest request, HttpServletResponse response, Object... parameters) {
            ActionResult actionResult = null;
            /** 
             * This might not be really stateless (could cause concurrent access problems)
             * alternative would to pass that as parameters to the action call
             */
            setRequest(request);
            setResponse(response);
            
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
                    // Don't look at me : http://stackoverflow.com/a/1149721/1478467
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    actionResult = new ActionResult("500 Server error: "+sw.toString(), ActionResult.TYPE_STRING + ActionResult.HTTP_500);
                    actionResult.setError(e);
                    return actionResult;
            }

            return actionResult;
	}

        protected EntityManager createEntityManager() {
            if(_em != null) return _em;
            _emf = Persistence.createEntityManagerFactory("J2E_MarketPU");
            _em = _emf.createEntityManager();
            
            return _em;
        }
        protected void closeEntityManager() {
            if(_em == null) return;
            _em.close();
            _emf.close();
            _em = null;
            _emf = null;
        }
        
        protected HttpServletRequest getRequest() {
            return _request;
        }

        protected void setRequest(HttpServletRequest _request) {
            this._request = _request;
        }

        protected HttpServletResponse getResponse() {
            return _response;
        }

        protected void setResponse(HttpServletResponse _response) {
            this._response = _response;
        }
        
        protected String getParameter(String name) {
            return _request.getParameter(name);
        }
        protected String[] getParameterValues(String name) {
            return _request.getParameterValues(name);
        }
}
