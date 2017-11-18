package tesis.com.py.sisgourmetmobile.request;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.onlinemaps.AllMenuData;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.SaveDataAsyncTask;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

import static tesis.com.py.sisgourmetmobile.notifications.BuildNotification.BuildNotification;

/**
 * Created by Manu0 on 11/17/2017.
 */

public class HomeDataRequest {

    private Context mContext;
    private String mMessageProcess;
    private ResponseInterface mInterface;

    public interface ResponseInterface {
        void processFinish(String status, String message);
    }


    public HomeDataRequest(ResponseInterface responseInterface, Context context) {
        this.mInterface = responseInterface;
        this.mContext = context;
        sendRequest();
    }


    private void sendRequest() {

        String REQUEST_TAG = "SEND_REQUEST_ALL_DATA";
        boolean mIsConnected;


        mIsConnected = Utils.checkNetworkConnection(mContext);
        if (!mIsConnected) {
            mMessageProcess = mContext.getString(R.string.tag_not_internet);
            mInterface.processFinish(Constants.NO_CONECTION_ERROR, mMessageProcess);

            return;
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URLS.ALL_MENU_DATA_URL,
                buildParams(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        responseHandler(response);
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

    private JSONObject buildParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("username", UserRepository.getUser(mContext).getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }


    private void responseHandler(JSONObject response) {

        List<Provider> mProviderList = new ArrayList<>();
        List<Lunch> mLunchList = new ArrayList<>();
        List<Garnish> mGarnishList = new ArrayList<>();
        List<Drinks> mDrinkList = new ArrayList<>();
        JSONArray mMainMenuArray;
        JSONArray mGarnishArray;
        JSONArray mProviderArray;
        JSONArray mDrinkArray;
        int status = 0;
        String message = "";

        if (response == null) {
            mMessageProcess = mContext.getString(R.string.tag_not_internet);
            mInterface.processFinish(Constants.SERVER_ERROR, mMessageProcess);
            return;
        }

        try {
            if (response.has("status")) status = response.getInt("status");

            if (response.has("message")) message = response.getString("message");

            if (status != 1) {
                mMessageProcess = message;
                mInterface.processFinish(Constants.SERVER_ERROR, mMessageProcess);
                return;
            } else {
                if (response.has("menu_principal_list")) {
                    mMainMenuArray = response.getJSONArray("menu_principal_list");
                    if (mMainMenuArray.length() > 0) {
                        for (int i = 0; i < mMainMenuArray.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) mMainMenuArray.get(i);
                                Lunch lunchObject = AllMenuData.getMainMenuData(jsonObject);
                                mLunchList.add(lunchObject);
                            } catch (JSONException jsx) {
                                jsx.printStackTrace();
                            }
                        }
                    }
                }
                if (response.has("guarnicion_list")) {
                    mGarnishArray = response.getJSONArray("guarnicion_list");
                    if (mGarnishArray.length() > 0) {
                        for (int i = 0; i < mGarnishArray.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) mGarnishArray.get(i);
                                Garnish garnishObject = AllMenuData.getGarnishData(jsonObject);
                                mGarnishList.add(garnishObject);
                            } catch (JSONException jsx) {
                                jsx.printStackTrace();
                            }
                        }
                    }
                }

                if (response.has("provider_list")) {
                    mProviderArray = response.getJSONArray("provider_list");
                    if (mProviderArray.length() > 0) {
                        for (int i = 0; i < mProviderArray.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) mProviderArray.get(i);
                                Provider providerObject = AllMenuData.getProviderData(jsonObject);
                                mProviderList.add(providerObject);
                            } catch (JSONException jsx) {
                                jsx.printStackTrace();
                            }
                        }
                    }
                }

                if (response.has("drink_list")) {
                    mDrinkArray = response.getJSONArray("drink_list");
                    if (mDrinkArray.length() > 0) {
                        for (int i = 0; i < mDrinkArray.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) mDrinkArray.get(i);
                                Drinks drinksObject = AllMenuData.getDrinkData(jsonObject);
                                mDrinkList.add(drinksObject);
                            } catch (JSONException jsx) {
                                jsx.printStackTrace();
                            }
                        }
                    }
                }
                if (mLunchList.size() > 0 && mGarnishList.size() > 0 && mProviderList.size() > 0 && mDrinkList.size() > 0) {
                    SaveDataAsyncTask saveDataAsyncTask = new SaveDataAsyncTask(mContext, new SaveDataAsyncTask.AsyncResponse() {
                        @Override
                        public void processFinish(Boolean status) {
                            if (status) {
                                mInterface.processFinish(Constants.ACTION_VIEW_DATA, "");
                                AppPreferences.getAppPreferences(mContext).edit().putBoolean(AppPreferences.KEY_SYNC_DATA, true).apply();
                            } else {
                                mMessageProcess = mContext.getString(R.string.internal_processing_error_data);
                                mInterface.processFinish(Constants.SERVER_ERROR, mMessageProcess);

                            }
                        }
                    }, mProviderList, mLunchList, mGarnishList, mDrinkList);
                    saveDataAsyncTask.execute();
                } else {
                    mMessageProcess = mContext.getString(R.string.tag_error_home_data);
                    mInterface.processFinish(Constants.SERVER_ERROR, mMessageProcess);
                    return;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public static class SaveDataAsyncTask extends AsyncTask<Void, Void, Boolean> {

        public interface AsyncResponse {
            void processFinish(Boolean status);
        }

        AsyncResponse delegate = null;
        private Context mContext;
        private List<Provider> mProviderList = new ArrayList<>();
        private List<Lunch> mLunchList = new ArrayList<>();
        private List<Garnish> mGarnishList = new ArrayList<>();
        private List<Drinks> mDrinkList = new ArrayList<>();


        public SaveDataAsyncTask(Context context, AsyncResponse delegate, List<Provider> providerList, List<Lunch> lunchList, List<Garnish> garnishList, List<Drinks> drinksList) {
            this.mContext = context;
            this.mProviderList = providerList;
            this.delegate = delegate;
            this.mLunchList = lunchList;
            this.mGarnishList = garnishList;
            this.mDrinkList = drinksList;
        }

        @Override
        protected void onPreExecute() {
            ProviderRepository.getDao().deleteAll();
            LunchRepository.getDao().deleteAll();
            GarnishRepository.getDao().deleteAll();
            DrinksRepository.getDao().deleteAll();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            int providerCount = 0;
            int lunchCount = 0;
            int garnishCount = 0;
            int drinksCount = 0;
            boolean status;

            for (Provider provider : mProviderList) {
                providerCount++;
                ProviderRepository.store(provider);
            }
            for (Lunch lunch : mLunchList) {
                lunchCount++;
                LunchRepository.store(lunch);
            }
            for (Garnish garnish : mGarnishList) {
                garnishCount++;
                GarnishRepository.store(garnish);
            }
            for (Drinks drinks : mDrinkList) {
                drinksCount++;
                DrinksRepository.store(drinks);
            }

            status = providerCount == mProviderList.size() && lunchCount == mLunchList.size() && garnishCount == mGarnishList.size() && drinksCount == mDrinkList.size();

            return status;
        }


        @Override
        protected void onPostExecute(Boolean status) {
            delegate.processFinish(status);
        }
    }

}
