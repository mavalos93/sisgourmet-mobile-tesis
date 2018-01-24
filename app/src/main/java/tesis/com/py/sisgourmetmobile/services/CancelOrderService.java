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

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.repositories.OrderRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.support.UserControlAmount;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 12/11/2017.
 */

public class CancelOrderService extends IntentService {
    private static final String TAG_CLASS = SendCommentService.class.getName();
    private static final String ORDER_ID = "ORDER_ID";
    private static final String REQUEST_TAG = "SEND_TRANSACTION";
    private NetworkQueue mQueue;

    public CancelOrderService() {
        super("CancelOrderService");
        mQueue = new NetworkQueue(this);
    }


    public static void startSendTransaction(Context context, long orderId) {
        Intent intent = new Intent(context, CancelOrderService.class);
        intent.putExtra(ORDER_ID, orderId);
        intent.setAction(Constants.ACTION_SEND_SINGLE);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            switch (action) {
                case Constants.ACTION_SEND_SINGLE:
                    long id = intent.getExtras().getLong(ORDER_ID);
                    Order sendOrder = OrderRepository.getById(id);
                    makeRequest(sendOrder);
                    break;
            }

        }
    }


    private void makeRequest(final Order order) {

        JsonObjectRequest momoJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLS.CANCEL_ORDER_URL, getParams(order), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleResponse(response, order);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = NetworkQueue.handleError(error, getApplicationContext());
                OrderRepository.updateOrderTransaction(CancelOrderService.this, order.getId(), order.getTransactionOrderId(), Constants.TRANSACTION_NO_CANCEL, message);
                Utils.builToast(getApplicationContext(), (message == null) ? getString(R.string.volley_default_error) : message);

            }
        });

        momoJsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
        momoJsonObjectRequest.setTag(REQUEST_TAG);
        NetworkQueue.getInstance(getApplicationContext()).addToRequestQueue(momoJsonObjectRequest, getApplicationContext());
    }

    private JSONObject getParams(Order order) {
        JSONObject params = new JSONObject();
        try {

            params.put("username", UserRepository.getUser(CancelOrderService.this).getUserName());
            params.put("order_id", order.getTransactionOrderId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return params;
    }

    protected void handleResponse(JSONObject response, Order order) {
        String message = null;
        int status = -1;

        if (response == null) {
            OrderRepository.updateOrderTransaction(CancelOrderService.this, order.getId(), order.getTransactionOrderId(), Constants.TRANSACTION_NO_CANCEL, getString(R.string.volley_parse_error));
            Utils.builToast(this, (message == null) ? getString(R.string.volley_default_error) : message);
            return;
        }

        Log.i(TAG_CLASS, REQUEST_TAG + " | Response: " + response.toString());

        try {
            if (response.has("status")) status = response.getInt("status");
            if (response.has("message")) message = response.getString("message");

            if (status != Constants.RESPONSE_OK) {
                OrderRepository.updateOrderTransaction(CancelOrderService.this, order.getId(), order.getTransactionOrderId(), Constants.TRANSACTION_NO_CANCEL, (message == null) ? getString(R.string.volley_default_error) : message);
                Utils.builToast(this, (message == null) ? getString(R.string.volley_default_error) : message);
                return;
            }

            UserControlAmount.deleteHistoryValue(CancelOrderService.this, order.getId());
            OrderRepository.updateOrderTransaction(CancelOrderService.this, order.getId(), order.getTransactionOrderId(), Constants.TRANSACTION_CANCEL, message);
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


