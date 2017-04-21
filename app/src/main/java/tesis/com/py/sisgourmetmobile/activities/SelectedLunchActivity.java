package tesis.com.py.sisgourmetmobile.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.ProviderRecyclerViewAdapter;
import tesis.com.py.sisgourmetmobile.adapters.SelectedMenuAdapter;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class SelectedLunchActivity extends AppCompatActivity {

    public static final String TAG_CLASS = SelectedLunchActivity.class.getName();

    // view
    private View coordinatorView;
    private View customeView;
    private RecyclerView mRecyclerView;
    private LayoutInflater mlayoutInflater;


    // adapters
    private SelectedMenuAdapter mSelectedMenuAdapter;

    // list
    private List<Lunch> mLunchList = new ArrayList<>();

    // Objects
    Provider mProviderObject = new Provider();
    Lunch mSelectedMenuObject = new Lunch();

    // Dialogs
    private AlertDialog mActionSelectedDialog;
    // Utilitarian Variable
    long mProviderId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_lunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_selected_lunch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        coordinatorView = findViewById(R.id.coordinator);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_lunch);
        mlayoutInflater = LayoutInflater.from(this);
        getProvider();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSelectedMenuAdapter = new SelectedMenuAdapter(new ArrayList<Lunch>(), this);
        mRecyclerView.setAdapter(mSelectedMenuAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mSelectedMenuObject = mSelectedMenuAdapter.getItemAtPosition(position);
                actionDialog(mSelectedMenuObject);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        chargeMenuDataList();

    }


    public void getProvider() {
        try {
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            if (bundle != null) {
                mProviderObject = (Provider) bundle.get(Constants.GET_PROVIDER_ACTION);
                if (mProviderObject != null) {
                    Log.d(TAG_CLASS, "PROVIDER: " + mProviderObject.toString());
                    mProviderId = mProviderObject.getId();
                } else {
                    Utils.builToast(this, getString(R.string.error_get_provider_by_recyclerview));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void chargeMenuDataList() {
        mLunchList = LunchRepository.getMenuByProviderId(mProviderId);
        Log.d(TAG_CLASS, "LIST_LUNCH: " + mLunchList.toString());
        if (mLunchList.size() == 0) {
            Utils.builToast(this, "Sin Menu para mostrar");
            finish();
        } else {
            mSelectedMenuAdapter = new SelectedMenuAdapter(mLunchList, this);
            mRecyclerView.setAdapter(mSelectedMenuAdapter);
        }
    }

    private void actionDialog(final Lunch lunch) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        customeView = mlayoutInflater.inflate(R.layout.action_lunch_dialog, null);
        TextView mProviderTextView = (TextView) customeView.findViewById(R.id.provider_menu_textView);
        TextView mMainMenuTextView = (TextView) customeView.findViewById(R.id.main_menu_selected_textView);
        TextView mGarnishTextView = (TextView) customeView.findViewById(R.id.garnish_selected_textView);

        builder.setView(customeView);
        builder.setPositiveButton(getString(R.string.label_drink), null);
        builder.setNegativeButton(getString(R.string.label_back), null);
        mActionSelectedDialog = builder.create();

        mActionSelectedDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button drinkButton = mActionSelectedDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button backButton = mActionSelectedDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                drinkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle menuBundle = new Bundle();
                        menuBundle.putSerializable(Constants.ACTION_SELECTED_MENU, lunch);
                        Intent menuIntent = new Intent(SelectedLunchActivity.this, OrderActivity.class);
                        menuIntent.putExtra(Constants.SERIALIZABLE, menuBundle);
                        startActivity(menuIntent);
                    }
                });

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActionSelectedDialog.dismiss();
                    }
                });

            }
        });




        mProviderTextView.setText("Proveedor: " + mProviderObject.getProviderName());
        mMainMenuTextView.setText("Menú Principal: " + mSelectedMenuObject.getMainMenuDescription());
        mGarnishTextView.setText("Guarnición: " + mSelectedMenuObject.getGarnishDescription());


        mActionSelectedDialog.setCanceledOnTouchOutside(false);
        mActionSelectedDialog.show();


    }

}
