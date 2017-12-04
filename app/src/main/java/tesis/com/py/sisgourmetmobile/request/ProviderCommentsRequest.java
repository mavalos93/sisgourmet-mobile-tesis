package tesis.com.py.sisgourmetmobile.request;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.entities.ProviderRating;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.onlinemaps.CommentsDataMapping;
import tesis.com.py.sisgourmetmobile.onlinemaps.ProviderDataMapping;
import tesis.com.py.sisgourmetmobile.repositories.AllCommentsRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRatingRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 11/25/2017.
 */

public class ProviderCommentsRequest {

    private Context mContext;
    private String mMessageProcess;
    private ResponseInterface mInterface;

    public interface ResponseInterface {
        void processFinish(String status, String message);
    }


    public ProviderCommentsRequest(ResponseInterface responseInterface, Context context) {
        this.mInterface = responseInterface;
        this.mContext = context;
        sendRequest();
    }

    private void sendRequest() {

        String REQUEST_TAG = "COMMENTS_REQUEST";
        boolean mIsConnected;

        mIsConnected = Utils.checkNetworkConnection(mContext);
        if (!mIsConnected) {
            mMessageProcess = mContext.getString(R.string.tag_not_internet);
            mInterface.processFinish(Constants.NO_CONECTION_ERROR, mMessageProcess);
            return;
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URLS.ALL_COMMENTS_URL,
                getParams(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mMessageProcess = NetworkQueue.handleError(error, mContext);
                        mInterface.processFinish(Constants.SERVER_ERROR, mMessageProcess);
                    }
                });
        jsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
        jsonObjectRequest.setTag(REQUEST_TAG);
        NetworkQueue.getInstance(mContext).addToRequestQueue(jsonObjectRequest, mContext);
    }

    private JSONObject getParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("username", UserRepository.getUser(mContext).getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }


    protected void handleResponse(JSONObject response) {


        String message = null;
        int status = -1;
        JSONArray providerArray = new JSONArray();
        JSONArray commentsArray = new JSONArray();

        if (response == null) {
            mInterface.processFinish(Constants.SERVER_ERROR, mContext.getString(R.string.volley_parse_error));
            return;
        }


        try {
            if (response.has("status")) status = response.getInt("status");
            if (response.has("message")) message = response.getString("message");

            if (status != Constants.RESPONSE_OK) {
                mInterface.processFinish(Constants.SERVER_ERROR, message);
                return;
            }
            if (response.has("provider_data")) {
                providerArray = response.getJSONArray("provider_data");

            }
            if (response.has("comments")) {
                commentsArray = response.getJSONArray("comments");
            }

            if (providerArray.length() > 0 && commentsArray.length() > 0) {
                ProviderComentsAsyncTask providerComentsAsyncTask = new ProviderComentsAsyncTask(new ProviderComentsAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(Boolean status) {
                        if (status) {
                            mInterface.processFinish(Constants.ACTION_VIEW_DATA, "");
                        } else {
                            mInterface.processFinish(Constants.SERVER_ERROR, mContext.getString(R.string.internal_processing_error_data));
                        }

                    }
                }, providerArray, commentsArray);
                providerComentsAsyncTask.execute();
            } else {
                mInterface.processFinish(Constants.SERVER_ERROR, mContext.getString(R.string.empty_required_data));
            }


        } catch (JSONException e) {
            e.printStackTrace();
            mInterface.processFinish(Constants.SERVER_ERROR, mContext.getString(R.string.error_parsing_json));
        }
    }


    public static class ProviderComentsAsyncTask extends AsyncTask<Void, Void, Boolean> {

        public interface AsyncResponse {
            void processFinish(Boolean status);
        }

        AsyncResponse delegate = null;
        private JSONArray mProviderArray = new JSONArray();
        private JSONArray mProviderComments = new JSONArray();

        public ProviderComentsAsyncTask(AsyncResponse delegate, JSONArray providerArray, JSONArray providerCommentsArray) {
            this.mProviderArray = providerArray;
            this.delegate = delegate;
            this.mProviderComments = providerCommentsArray;

        }

        @Override
        protected void onPreExecute() {
            ProviderRatingRepository.clearAll();
            AllCommentsRepository.clearAll();

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            int counterProviderData = 0;
            int counterComments = 0;


            for (int i = 0; i < mProviderArray.length(); i++) {
                counterProviderData++;
                try {
                    JSONObject jsonObject = mProviderArray.getJSONObject(i);
                    ProviderRating providerRating = ProviderDataMapping.getProviderDataFromJson(jsonObject);
                    ProviderRatingRepository.store(providerRating);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < mProviderComments.length(); j++) {
                counterComments++;
                try {
                    JSONObject jsonObject = mProviderComments.getJSONObject(j);
                    Comments cdm = new Comments();
                    cdm = CommentsDataMapping.getCommentsDataFromJson(jsonObject);
                    AllCommentsRepository.store(cdm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Log.d("TAG", "COMMENTS: " + mProviderComments.length());
            return counterProviderData == mProviderArray.length() && counterComments == mProviderComments.length();
        }


        @Override
        protected void onPostExecute(Boolean status) {
            delegate.processFinish(status);
        }
    }


}
