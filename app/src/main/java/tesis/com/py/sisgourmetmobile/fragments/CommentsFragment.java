package tesis.com.py.sisgourmetmobile.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.CommentsDetailActivity;
import tesis.com.py.sisgourmetmobile.adapters.ProviderQualificationAdapter;
import tesis.com.py.sisgourmetmobile.entities.ProviderComments;
import tesis.com.py.sisgourmetmobile.entities.ProviderRating;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.onlinemaps.ProviderDataMapping;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRatingRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.request.HomeDataRequest;
import tesis.com.py.sisgourmetmobile.request.ProviderCommentsRequest;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 11/22/2017.
 */

public class CommentsFragment extends Fragment {

    // VIEW
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mProvidersRecyclerView;
    private ProviderQualificationAdapter mProvidersDataAdapter;
    private LinearLayout mNotificationContainer;
    private ProgressBar mLoadProgressBar;
    private ImageView mNotificationImage;
    private TextView mNotificationDescription;
    private AppCompatButton mActionButton;

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.comments_fragment, container, false);
        mSwipeRefreshLayout = rootView.findViewById(R.id.comments_swipe_refresh_layout);

        mNotificationContainer = rootView.findViewById(R.id.notification_container);
        mLoadProgressBar = rootView.findViewById(R.id.progress_data);
        mNotificationImage = rootView.findViewById(R.id.notification_image);
        mNotificationDescription = rootView.findViewById(R.id.notification_message);
        mActionButton = rootView.findViewById(R.id.action_button);


        mProvidersRecyclerView = rootView.findViewById(R.id.providers_rating_recyclerview);
        mProvidersRecyclerView.setHasFixedSize(true);
        mProvidersDataAdapter = new ProviderQualificationAdapter(new ArrayList<ProviderRating>(), getContext());
        mProvidersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProvidersRecyclerView.setAdapter(mProvidersDataAdapter);
        mProvidersRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mProvidersRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Bundle providerBundle = new Bundle();
                providerBundle.putSerializable(Constants.ACTION_COMMENTS, mProvidersDataAdapter.getItemAtPosition(position));
                Intent intent = new Intent(getContext(), CommentsDetailActivity.class);
                intent.putExtra(Constants.SERIALIZABLE, providerBundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });
        actions();
        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        clearSwipeRefreshLayout();
    }

    private void clearSwipeRefreshLayout() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }
    }

    private void clearData() {
        mProvidersDataAdapter.setData(new ArrayList<ProviderRating>());
    }

    private void actions() {
        long counter = ProviderRatingRepository.cont();
        if (counter != 0) {
            mLoadProgressBar.setVisibility(View.GONE);
            mNotificationContainer.setVisibility(View.GONE);
            mProvidersRecyclerView.setVisibility(View.VISIBLE);
            setData();
        } else {
            sendRequest();
        }


    }

    private void sendRequest() {
        mProvidersRecyclerView.setVisibility(View.GONE);
        mNotificationContainer.setVisibility(View.GONE);
        mLoadProgressBar.setVisibility(View.VISIBLE);
        clearSwipeRefreshLayout();
        new ProviderCommentsRequest(new ProviderCommentsRequest.ResponseInterface() {
            @Override
            public void processFinish(String status, String message) {
                executingAction(status, message);
            }
        }, getContext());
    }


    private void executingAction(String action, String message) {


        switch (action) {
            case Constants.NO_CONECTION_ERROR:
                mLoadProgressBar.setVisibility(View.GONE);
                mNotificationContainer.setVisibility(View.VISIBLE);
                mNotificationImage.setBackgroundResource(R.mipmap.ic_perm_scan_wifi);
                mNotificationDescription.setText(message);
                mProvidersRecyclerView.setVisibility(View.GONE);
                break;
            case Constants.SERVER_ERROR:
                mLoadProgressBar.setVisibility(View.GONE);
                mNotificationContainer.setVisibility(View.VISIBLE);
                mNotificationImage.setBackgroundResource(R.mipmap.ic_error);
                mNotificationDescription.setText(message);
                mProvidersRecyclerView.setVisibility(View.GONE);
                break;
            case Constants.ACTION_VIEW_DATA:
                mLoadProgressBar.setVisibility(View.GONE);
                mNotificationContainer.setVisibility(View.GONE);
                mProvidersRecyclerView.setVisibility(View.VISIBLE);
                setData();
                break;
        }
    }


    private void setData() {
        clearData();
        mProvidersDataAdapter.setData(ProviderRatingRepository.getAll());
    }
}
