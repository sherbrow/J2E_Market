/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sherbrow
 */
public class ActionResult {

    public ActionResult(Object _result) {
        this(_result, TYPE_JSF, null);
    }
    public ActionResult(Object _result, Map<String, Object> viewBag) {
        this(_result, TYPE_JSF, viewBag);
    }
    public ActionResult(Object _result, int _type) {
        this(_result, _type, null);
    }
    public ActionResult(Object _result, int _type, Map<String, Object> viewBag) {
        this._type = _type;
        this._result = _result;
        if(viewBag != null) {
            this._viewBag = new HashMap<String, Object>(viewBag);
        }
        else {
            this._viewBag = new HashMap<String, Object>();
        }
    }
    
    private int _type;
    private Exception _error;
    private Object _result;
    protected Map<String, Object> _viewBag = null;


    /**
     * @return the _type
     */
    public int getType() {
        return _type;
    }

    /**
     * @param type the _type to set
     */
    public void setType(int type) {
        this._type = type;
    }


    /**
     * @param type the _type to set
     */
    public void addType(int type) {
        this._type = type + this._type;
    }

    /**
     * @return the _error
     */
    public Exception getError() {
        return _error;
    }
    
    /**
     * @param error the _error to set
     */
    public void setError(Exception error) {
        this._error = error;
    }
    
    public static final int  TYPE_JSP = 1;
    public static final int TYPE_STRING = 2;
    public static final int TYPE_JSF = 4;
    public static final int MASK_RENDERTYPE = 31;
    public static final int HTTP_401 = 32;
    public static final int HTTP_403 = 64;
    public static final int HTTP_404 = 128;
    public static final int HTTP_500 = 256;
    public static final int MASK_HTTPERROR = 481;
    public static final int TYPE_ERROR = 481;

    /**
     * @return the _result
     */
    public Object getResult() {
        return _result;
    }

    /**
     * @param result the _result to set
     */
    public void setResult(Object result) {
        this._result = result;
    }
    
    public Map<String, Object> getViewBag() {
        return _viewBag;
    }
    
}
