package tesis.com.py.sisgourmetmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.RecyclerViewDataAdapter;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.models.SectionDataModel;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;

/**
 * Created by Manu0 on 23/1/2017.
 */

public class MenuFragment extends Fragment {

    private OnItemMenuListener mListener;
    private View rootView;
    private RecyclerView myRecyclerView;
    private RecyclerViewDataAdapter mAdapter;
    private ArrayList<SectionDataModel> allSampleData;


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
                    + " must implement OnItemTransactioListenerSelected");
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


