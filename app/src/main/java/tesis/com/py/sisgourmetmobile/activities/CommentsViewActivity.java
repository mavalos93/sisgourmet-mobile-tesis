package tesis.com.py.sisgourmetmobile.activities;

import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.AllCommentsAdapter;
import tesis.com.py.sisgourmetmobile.adapters.ProviderQualificationAdapter;
import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.models.ProviderQualificationModel;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.onlinemaps.CommentsDataMapping;
import tesis.com.py.sisgourmetmobile.onlinemaps.ProviderDataMapping;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class CommentsViewActivity extends AppCompatActivity {

    private final String TAG_CLASS = CommentsViewActivity.class.getName();

    //View


    private RecyclerView mAllCommentsRecyclerView;
    private RecyclerView mProviderQualificationRecyclerView;


    // Adapter
    private AllCommentsAdapter mAllCommentsAdapter;
    private ProviderQualificationAdapter mProviderDataAdapter;

    // Network and Request
    private SwipeRefreshLayout mSwipeRefreshLyout;
    private RequestQueue mRequestQueue;
    private String REQUEST_TAG = "ALL_COMMENTS_REQUEST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commets_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSwipeRefreshLyout = (SwipeRefreshLayout) findViewById(R.id.swipe_comment_refresh);

        mSwipeRefreshLyout.setColorSchemeResources(
                R.color.colorRed,
                R.color.colorBlue,
                R.color.accent);

        mSwipeRefreshLyout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });

    }


    private void setupDataAdapters(List<Comments> commentsList, List<ProviderQualificationModel> providerDataList) {

        mProviderQualificationRecyclerView = (RecyclerView) findViewById(R.id.all_comments_recyclerView);
        mProviderQualificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProviderQualificationRecyclerView.setNestedScrollingEnabled(false);


        mProviderDataAdapter = new ProviderQualificationAdapter(new ArrayList<ProviderQualificationModel>(), this);
        mProviderQualificationRecyclerView.setAdapter(mProviderDataAdapter);
        mProviderQualificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mProviderQualificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mProviderQualificationRecyclerView.setHasFixedSize(true);
        mProviderQualificationRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mProviderQualificationRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        mProviderDataAdapter.setData(providerDataList);


        mAllCommentsRecyclerView = (RecyclerView) findViewById(R.id.provider_qualification_data_recyclerView);
        mAllCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAllCommentsRecyclerView.setNestedScrollingEnabled(false);


        mAllCommentsAdapter = new AllCommentsAdapter(new ArrayList<Comments>(), this);
        mAllCommentsRecyclerView.setAdapter(mAllCommentsAdapter);
        mAllCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAllCommentsRecyclerView.setHasFixedSize(true);
        mAllCommentsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mAllCommentsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        mAllCommentsAdapter.setData(commentsList);
        mSwipeRefreshLyout.setRefreshing(false);


    }


   /* private void setupProviderData(int mVienesaMaxValue, int mBolsiMaxValue, int mNhaEustaquiaMaxValue, List<Comments> commentsList) {
        // Charge all comment data
        // setup view this provider (Vienesa)
        Utils.ProgressBarAnimation animVienesa = new Utils.ProgressBarAnimation(mVienesaProgressBar, 0, mVienesaMaxValue);
        animVienesa.setDuration(1000);
        mVienesaProgressBar.startAnimation(animVienesa);
        Utils.ProgressBarAnimation animBolsi = new Utils.ProgressBarAnimation(mBolsiProgressBar, 0, mBolsiMaxValue);
        animBolsi.setDuration(1000);
        mBolsiProgressBar.startAnimation(animBolsi);
        Utils.ProgressBarAnimation animNhaEustaquia = new Utils.ProgressBarAnimation(mNhaEustaquiaProgressBar, 0, mNhaEustaquiaMaxValue);
        animNhaEustaquia.setDuration(1000);
        mNhaEustaquiaProgressBar.startAnimation(animNhaEustaquia);

    }*/


    private void sendRequest() {
        boolean mIsConnected;

        mIsConnected = Utils.checkNetworkConnection(this);
        if (!mIsConnected) {
            Utils.builToast(this, getString(R.string.tag_not_internet));
            return;
        }
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(REQUEST_TAG);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URLS.ALL_COMMENTS_URL,
                getParams(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mSwipeRefreshLyout.setRefreshing(false);
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mSwipeRefreshLyout.setRefreshing(false);
                        String message = NetworkQueue.handleError(error, getApplicationContext());
                        Utils.builToast(CommentsViewActivity.this, message);
                    }
                });
        jsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
        jsonObjectRequest.setTag(REQUEST_TAG);
        NetworkQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, getApplicationContext());
    }


    protected void handleResponse(JSONObject response) {

        String message = null;
        int status = -1;
        JSONArray providerArray;
        JSONArray commentsArray;

        if (response == null) {
            Utils.builToast(CommentsViewActivity.this, getString(R.string.volley_parse_error));
            finish();
            return;
        }

        Log.i(TAG_CLASS, REQUEST_TAG + " | Response: " + response.toString());

        try {
            if (response.has("status")) status = response.getInt("status");
            if (response.has("message")) message = response.getString("message");

            if (status != Constants.RESPONSE_OK) {
                Utils.builToast(CommentsViewActivity.this, message);
                return;
            }
            List<ProviderQualificationModel> providerList = new ArrayList<>();
            if (response.has("provider_data")) {
                providerArray = response.getJSONArray("provider_data");
                if (providerArray.length() > 0) {
                    for (int i = 0; i < providerArray.length(); i++) {
                        try {
                            JSONObject jsonObject = providerArray.getJSONObject(i);
                            ProviderQualificationModel pqm = ProviderDataMapping.getProviderDataFromJson(jsonObject);
                            providerList.add(pqm);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mSwipeRefreshLyout.setRefreshing(false);
                    Utils.builToast(this, getString(R.string.error_provider_data_null));
                    return;
                }
            }
            List<Comments> commentsList = new ArrayList<>();
            if (response.has("comments")) {
                commentsArray = response.getJSONArray("comments");
                if (commentsArray.length() > 0) {
                    for (int j = 0; j < commentsArray.length(); j++) {
                        try {
                            JSONObject jsonObject = commentsArray.getJSONObject(j);
                            Comments cdm = CommentsDataMapping.getCommentsDataFromJson(jsonObject);
                            commentsList.add(cdm);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mSwipeRefreshLyout.setRefreshing(false);
                    Utils.builToast(this, getString(R.string.error_all_comments_null));
                    return;
                }

            }

            setupDataAdapters(commentsList, providerList);


        } catch (JSONException e) {
            e.printStackTrace();
            Utils.builToast(CommentsViewActivity.this, getString(R.string.error_parsing_json));
        }
    }


    private JSONObject getParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("username", AppPreferences.getAppPreferences(this).getString(AppPreferences.KEY_PREFERENCE_USER, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }


}
