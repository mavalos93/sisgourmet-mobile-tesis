package tesis.com.py.sisgourmetmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.RecyclerViewDataAdapter;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.models.SectionDataModel;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.request.HomeDataRequest;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Constants;

/**
 * Created by Manu0 on 23/1/2017.
 */

public class MenuFragment extends Fragment {

    // VIEW
    private View rootView;
    private RecyclerView myRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private ImageView mNotificationImageView;
    private TextView mMessageTextView;
    private LinearLayout mNotificationContainer;
    private RelativeLayout mContainerData;
    private AppCompatButton mActionButton;


    // ADAPTERS AND LISTENERS

    private OnItemMenuListener mListener;
    private RecyclerViewDataAdapter mAdapter;

    // UTILITARIAN VARIABLE
    private List<SectionDataModel> allSampleData = new ArrayList<>();


    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
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
        mSwipeRefreshLayout = rootView.findViewById(R.id.menu_refresh_layout);

        mContainerData = rootView.findViewById(R.id.container_home_data);
        mProgressBar = rootView.findViewById(R.id.load_data_progress);
        mNotificationImageView = rootView.findViewById(R.id.notification_image);
        mMessageTextView = rootView.findViewById(R.id.description_notification);
        mNotificationContainer = rootView.findViewById(R.id.notification_container);
        mActionButton = rootView.findViewById(R.id.action_button);


        myRecyclerView = rootView.findViewById(R.id.my_recycler_view);
        myRecyclerView.setHasFixedSize(true);
        mAdapter = new RecyclerViewDataAdapter(getContext(), new ArrayList<SectionDataModel>());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setAdapter(mAdapter);
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


    @Override
    public void onPause() {
        super.onPause();
        clearSwipeRefreshLayout();
    }


    public interface OnItemMenuListener {
        // TODO: Update argument type and name
        void onItemMenuSelectedListener(Menu menu);
    }


    private void clearData() {
        allSampleData.clear();
    }

    private void clearSwipeRefreshLayout() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.destroyDrawingCache();
            mSwipeRefreshLayout.clearAnimation();
        }
    }


    private void actions() {
        boolean statusData = AppPreferences.getAppPreferences(getContext()).getBoolean(AppPreferences.KEY_SYNC_DATA, false);
            if (statusData) {
                mProgressBar.setVisibility(View.GONE);
                mNotificationContainer.setVisibility(View.GONE);
                mContainerData.setVisibility(View.VISIBLE);
                setupData();
            } else {
                sendRequest();
            }


    }


    private void sendRequest() {
        mContainerData.setVisibility(View.GONE);
        mNotificationContainer.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        new HomeDataRequest(new HomeDataRequest.ResponseInterface() {
            @Override
            public void processFinish(String action, String message) {
                executingAction(action, message);
            }
        }, getContext());
    }


    private void executingAction(String action, String message) {

        switch (action) {
            case Constants.NO_CONECTION_ERROR:
                mProgressBar.setVisibility(View.GONE);
                mNotificationContainer.setVisibility(View.VISIBLE);
                mNotificationImageView.setBackgroundResource(R.mipmap.ic_perm_scan_wifi);
                mMessageTextView.setText(message);
                break;
            case Constants.SERVER_ERROR:
                mProgressBar.setVisibility(View.GONE);
                mNotificationContainer.setVisibility(View.VISIBLE);
                mNotificationImageView.setBackgroundResource(R.mipmap.ic_error);
                mMessageTextView.setText(message);
                break;
            case Constants.ACTION_VIEW_DATA:
                clearSwipeRefreshLayout();
                mProgressBar.setVisibility(View.GONE);
                mContainerData.setVisibility(View.VISIBLE);
                setupData();
                break;
        }
    }


    public void setupData() {
        clearData();
        List<Provider> providerList = ProviderRepository.getAllProvider();
        for (Provider pr : providerList) {
            SectionDataModel dm = new SectionDataModel();
            dm.setHeaderTitle(pr.getProviderName());
            List<Lunch> lunchItem = LunchRepository.getMenuByProviderId(pr.getProviderId());
            ArrayList<Lunch> newItemLunch = new ArrayList<>();
            for (Lunch lu : lunchItem) {
                newItemLunch.add(lu);
            }
            dm.setAllItemsInSection(newItemLunch);
            allSampleData.add(dm);
            mAdapter.setData(allSampleData);
        }
    }


}


