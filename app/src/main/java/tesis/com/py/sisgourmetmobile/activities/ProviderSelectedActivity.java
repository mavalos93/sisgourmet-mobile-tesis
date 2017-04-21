package tesis.com.py.sisgourmetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.ProviderRecyclerViewAdapter;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class ProviderSelectedActivity extends AppCompatActivity {

    public static final String TAG_CLASS = ProviderSelectedActivity.class.getName();

    // view
    private View coordinatorView;
    private View customeView;
    private RecyclerView mRecyclerView;
    private LayoutInflater mlayoutInflater;


    // adapters
    private ProviderRecyclerViewAdapter mProviderAdapter;

    // list
    private List<Provider> providerListQuery = new ArrayList<>();

    // Objects
    Provider mProvider = new Provider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_selected);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_provider_selected);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        coordinatorView = findViewById(R.id.coordinator);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_selected_provider);
        mlayoutInflater = LayoutInflater.from(this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProviderAdapter = new ProviderRecyclerViewAdapter(new ArrayList<Provider>());
        mRecyclerView.setAdapter(mProviderAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mProvider = mProviderAdapter.getItemAtPosition(position);
                setProviderDataToActivity(mProvider);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        chargeProviderList();


    }


    private void chargeProviderList() {
        providerListQuery = ProviderRepository.getAllProvider();
        if (providerListQuery == null) {
            Utils.builToast(this, "Sin proveedores para mostrar");
        } else {
            mProviderAdapter = new ProviderRecyclerViewAdapter(providerListQuery);
            mRecyclerView.setAdapter(mProviderAdapter);
        }
    }

    private void setProviderDataToActivity(Provider providerObject){
        Bundle providerBundle = new Bundle();
        providerBundle.putSerializable(Constants.GET_PROVIDER_ACTION, providerObject);
        Intent providerIntent = new Intent(ProviderSelectedActivity.this, SelectedLunchActivity.class);
        providerIntent.putExtra(Constants.SERIALIZABLE, providerBundle);
        startActivity(providerIntent);
    }


}


