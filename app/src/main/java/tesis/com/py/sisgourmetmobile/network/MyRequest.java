package tesis.com.py.sisgourmetmobile.network;

import org.json.JSONObject;

import tesis.com.py.sisgourmetmobile.dialogs.ProgressDialogFragment;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;

/**
 * Created by diego on 18/10/16.
 */

public abstract class MyRequest {

    protected ProgressDialogFragment progressDialog;
    protected JsonObjectRequest jsonObjectRequest;


    protected abstract void handleResponse(JSONObject response);

    protected abstract void confirm();

    protected abstract void execute();

    public abstract class RequestObject {
        public abstract JSONObject getParams();
    }

    public JsonObjectRequest getJsonObjectRequest() {
        return jsonObjectRequest;
    }

}
