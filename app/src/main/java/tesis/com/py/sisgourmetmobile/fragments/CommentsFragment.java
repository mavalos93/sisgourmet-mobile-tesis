package tesis.com.py.sisgourmetmobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.ProviderQualificationAdapter;
import tesis.com.py.sisgourmetmobile.entities.ProviderRating;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.onlinemaps.ProviderDataMapping;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRatingRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 11/22/2017.
 */

public class CommentsFragment extends Fragment {

    // VIEW
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLyout;
    private RecyclerView mProvidersRecyclerView;
    private RecyclerView mCommentsRecyclerView;
    private ProviderQualificationAdapter mProvidersDataAdapter;

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.comments_fragment, container, false);
        mSwipeRefreshLyout = rootView.findViewById(R.id.comments_swipe_refresh_layout);

        mProvidersRecyclerView = rootView.findViewById(R.id.providers_rating_recyclerview);
        mProvidersRecyclerView.setHasFixedSize(true);
        mProvidersDataAdapter = new ProviderQualificationAdapter(new ArrayList<ProviderRating>(), getContext());
        mProvidersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProvidersRecyclerView.setAdapter(mProvidersDataAdapter);


        mSwipeRefreshLyout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });
        setData();

        return rootView;
    }


    private void sendRequest() {

        String REQUEST_TAG = "COMMENTS_REQUEST";
        boolean mIsConnected;

        mIsConnected = Utils.checkNetworkConnection(getContext());
        if (!mIsConnected) {
            Utils.builToast(getContext(), getString(R.string.tag_not_internet));
            return;
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
                        String message = NetworkQueue.handleError(error, getContext());
                        Utils.builToast(getContext(), message);
                    }
                });
        jsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
        jsonObjectRequest.setTag(REQUEST_TAG);
        NetworkQueue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest, getContext());
    }


    protected void handleResponse(JSONObject response) {


        String message = null;
        int status = -1;
        JSONArray providerArray;
        JSONArray commentsArray;

        if (response == null) {
            Utils.builToast(getContext(), getString(R.string.volley_parse_error));
            getActivity().finish();
            return;
        }


        try {
            if (response.has("status")) status = response.getInt("status");
            if (response.has("message")) message = response.getString("message");

            if (status != Constants.RESPONSE_OK) {
                Utils.builToast(getContext(), message);
                return;
            }
            if (response.has("provider_data")) {
                providerArray = response.getJSONArray("provider_data");
                if (providerArray.length() > 0) {
                    for (int i = 0; i < providerArray.length(); i++) {
                        try {
                            JSONObject jsonObject = providerArray.getJSONObject(i);
                            ProviderRating providerRating = ProviderDataMapping.getProviderDataFromJson(jsonObject);
                            ProviderRatingRepository.store(providerRating);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mSwipeRefreshLyout.setRefreshing(false);
                    Utils.builToast(getContext(), getString(R.string.error_provider_data_null));
                    return;
                }
            }


            setData();


        } catch (JSONException e) {
            e.printStackTrace();
            Utils.builToast(getContext(), getString(R.string.error_parsing_json));
        }
    }


    private JSONObject getParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("username", UserRepository.getUser(getContext()).getUserName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private void setData() {
        mProvidersDataAdapter.setData(ProviderRatingRepository.getAll());
    }



}
