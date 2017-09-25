package tesis.com.py.sisgourmetmobile.fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.RecyclerViewDataAdapter;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.models.SectionDataModel;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.SaveDataAsyncTask;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

import static tesis.com.py.sisgourmetmobile.notifications.BuildNotification.BuildNotification;

/**
 * Created by Manu0 on 23/1/2017.
 */

public class MenuFragment extends Fragment {

    // VIEW
    private View rootView;
    private RecyclerView myRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // ADAPTERS AND LISTENERS

    private OnItemMenuListener mListener;
    private RecyclerViewDataAdapter mAdapter;

    // UTILITARIAN VARIABLE
    private ArrayList<SectionDataModel> allSampleData;
    private List<Provider> mProviderList = new ArrayList<>();
    private List<Lunch> mLunchList = new ArrayList<>();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private List<Drinks> mDrinkList = new ArrayList<>();
    private JsonObjectRequest mJsonObjectRequest = null;
    private RequestQueue mRequestQueue = null;
    private NotificationCompat.Builder mNotificationBuild;
    private NotificationManager mNotificationManager;


    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.menu_refresh_layout);

        allSampleData = new ArrayList<>();


        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        myRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewDataAdapter(getContext(), allSampleData);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(mAdapter);
        createDummyData();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemMenuListener) {
            mListener = (OnItemMenuListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemMenuListenerSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnItemMenuListener {
        // TODO: Update argument type and name
        void onItemMenuSelectedListener(Menu menu);
    }

    private void sendRequest(String mUsername) {

        String REQUEST_TAG = "SEND_REQUEST_ALL_DATA";
        boolean mIsConnected;


        mIsConnected = Utils.checkNetworkConnection(getContext());
        if (!mIsConnected) {
            Utils.builToast(getContext(), getString(R.string.tag_not_internet));
            return;
        }
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(REQUEST_TAG);
        }

        String mUrl = URLS.ALL_MENU_DATA_URL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URLS.ALL_COMMENTS_URL,
                buildParams(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        responseHandler(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        String message = NetworkQueue.handleError(error, getContext());
                        Utils.builToast(getContext(), message);
                    }
                });
        jsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
        jsonObjectRequest.setTag(REQUEST_TAG);
        NetworkQueue.getInstance(getContext()).addToRequestQueue(jsonObjectRequest, getContext());


    }

    private void responseHandler(JSONObject response) {


        SaveDataAsyncTask incidentStorage = new SaveDataAsyncTask(getContext(), new SaveDataAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(Boolean status) {
                if (status) {

                    mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationBuild = BuildNotification(getContext(),
                            R.mipmap.ic_done_black_36dp,
                            getString(R.string.tag_initial_data),
                            getString(R.string.tag_initial_data_success_message));
                    mNotificationManager.notify(10, mNotificationBuild.build());
                }
            }
        }, mProviderList, mLunchList, mGarnishList, mDrinkList);
        incidentStorage.execute();

    }


    private JSONObject buildParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("username", AppPreferences.getAppPreferences(getContext()).getString(AppPreferences.KEY_PREFERENCE_USER, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

    public void createDummyData() {
        List<Provider> providerList = ProviderRepository.getAllProvider();
        for (Provider pr : providerList) {

            SectionDataModel dm = new SectionDataModel();
            dm.setHeaderTitle(pr.getProviderName());
            List<Lunch> lunchItem = LunchRepository.getMenuByProviderId(pr.getId());
            ArrayList<Lunch> newItemLunch = new ArrayList<>();

            for (Lunch lu : lunchItem) {
                newItemLunch.add(lu);
            }
            dm.setAllItemsInSection(newItemLunch);
            allSampleData.add(dm);

        }
    }


}


