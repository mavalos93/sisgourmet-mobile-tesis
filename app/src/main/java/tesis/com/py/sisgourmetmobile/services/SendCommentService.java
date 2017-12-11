package tesis.com.py.sisgourmetmobile.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 12/10/2017.
 */

public class SendCommentService extends IntentService {
    private static final String TAG_CLASS = SendCommentService.class.getName();
    private static final String COMMENT_ID = "COMMENT_ID";
    private static final String REQUEST_TAG = "SEND_TRANSACTION";
    private NetworkQueue mQueue;

    public SendCommentService() {
        super("SendCommentService");
        mQueue = new NetworkQueue(this);
    }


    public static void startSendTransaction(Context context, long commentId) {
        Intent intent = new Intent(context, SendOrderService.class);
        intent.putExtra(COMMENT_ID, commentId);
        intent.setAction(Constants.ACTION_SEND_SINGLE);
        context.startService(intent);
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case Constants.ACTION_SEND_SINGLE:
                    long id = intent.getExtras().getLong(COMMENT_ID);
                    Qualification sendQualification = QualificationRepository.getById(id);
                    makeRequest(sendQualification);
                    break;
            }

        }
    }


    private void makeRequest(final Qualification qualification) {
        JSONObject body = null;
        try {
            body = new JSONObject(qualification.getHttpDetail());
        } catch (JSONException jEX) {
            jEX.printStackTrace();
            Log.w(TAG_CLASS, "Error while create JSONObject " + jEX.getMessage());

        }
        JsonObjectRequest momoJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLS.QUALIFICATION_URL, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleResponse(response, qualification);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = NetworkQueue.handleError(error, getApplicationContext());
                QualificationRepository.updateCommentTransaction(SendCommentService.this,qualification.getId(), Constants.TRANSACTION_NO_SEND, message);
                Utils.builToast(getApplicationContext(), (message == null) ? getString(R.string.volley_default_error) : message);

            }
        });

        momoJsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
        momoJsonObjectRequest.setTag(REQUEST_TAG);
        NetworkQueue.getInstance(getApplicationContext()).addToRequestQueue(momoJsonObjectRequest, getApplicationContext());
    }

    protected void handleResponse(JSONObject response, Qualification qualification) {
        String message = null;
        int status = -1;

        if (response == null) {
            QualificationRepository.updateCommentTransaction(SendCommentService.this,qualification.getId(), Constants.TRANSACTION_NO_SEND, getString(R.string.volley_parse_error));
            Utils.builToast(this, (message == null) ? getString(R.string.volley_default_error) : message);
            return;
        }

        Log.i(TAG_CLASS, REQUEST_TAG + " | Response: " + response.toString());

        try {
            if (response.has("status")) status = response.getInt("status");
            if (response.has("message")) message = response.getString("message");

            if (status != Constants.RESPONSE_OK) {
                QualificationRepository.updateCommentTransaction(SendCommentService.this,qualification.getId(), Constants.TRANSACTION_NO_SEND, (message == null) ? getString(R.string.volley_default_error) : message);
                Utils.builToast(this, (message == null) ? getString(R.string.volley_default_error) : message);
                return;
            }

            QualificationRepository.updateCommentTransaction(SendCommentService.this,qualification.getId(), Constants.TRANSACTION_SEND, message);
            Utils.builToast(this, message);
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.builToast(this, getString(R.string.error_parsing_json));
        } catch (Exception ex) {
            ex.printStackTrace();
            Utils.builToast(this, getString(R.string.volley_default_error));
        }

    }
}


