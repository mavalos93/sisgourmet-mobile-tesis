package tesis.com.py.sisgourmetmobile.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.DrinksAdapter;
import tesis.com.py.sisgourmetmobile.adapters.ProviderRecyclerViewAdapter;
import tesis.com.py.sisgourmetmobile.adapters.SummaryDrinksAdapter;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class OrderActivity extends AppCompatActivity {

    public static final String TAG_CLASS = OrderActivity.class.getName();

    // View
    private RecyclerView mDrinksRecyclerView;
    private DrinksAdapter mSelectedDrinksAdapter;
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private AppCompatButton mSummaryButton;
    private AppCompatButton mSelectedLunchButton;
    private TextView mSelectedMainMenuTextView;
    private TextView mSelectedGarnishTextView;
    private TextView mSelectedQualificationTextView;
    private AppCompatRatingBar mRatingMenu;
    private AppCompatCheckBox mDrinkSelectedCheckBox;
    private LinearLayout mDrinkRecyclerViewContainer;


    // Objects
    Lunch mSelectedMenuObject = new Lunch();

    // List
    List<Drinks> mDrinkListItem = new ArrayList<>();
    List<Drinks> drinkReturnList = new ArrayList<>();

    // Utilitarian Variable
    boolean selectedDrinks = false;



    // Dialogs
    private Dialog summaryDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrinksRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_selected_drinks);
        mSummaryButton = (AppCompatButton) findViewById(R.id.action_summary_button);
        mSelectedMainMenuTextView = (TextView) findViewById(R.id.selected_main_menu_textView);
        mSelectedGarnishTextView = (TextView) findViewById(R.id.selected_garnish_textView);
        mSelectedQualificationTextView = (TextView) findViewById(R.id.selected_rating_description_textView);
        mRatingMenu = (AppCompatRatingBar) findViewById(R.id.selected_provider_rating_menu);
        mSelectedLunchButton = (AppCompatButton) findViewById(R.id.action_back_button);
        mDrinkSelectedCheckBox = (AppCompatCheckBox) findViewById(R.id.selected_drink_checkBox);
        mDrinkRecyclerViewContainer = (LinearLayout) findViewById(R.id.drinks_recyclerView_container);
        mlayoutInflater = LayoutInflater.from(this);

        getMainOrder();
        mDrinksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSelectedDrinksAdapter = new DrinksAdapter(mDrinkListItem);
        mDrinksRecyclerView.setAdapter(mSelectedDrinksAdapter);
        mDrinksRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mDrinksRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mDrinksRecyclerView.setHasFixedSize(true);
        mDrinksRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mDrinksRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                drinkReturnList = mSelectedDrinksAdapter.getSelectedDrinks();
                Log.d(TAG_CLASS, "nueva_lista: " + drinkReturnList.toString());

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        mSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedDrinks && drinkReturnList.size() == 0){
                    Utils.builToast(OrderActivity.this,"Debes seleccionar una bebida");
                }else  {
                    summaryLunchDialog();
                }
            }
        });

        mSelectedLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderActivity.this, SelectedLunchActivity.class));
                finish();
            }
        });

        mDrinkRecyclerViewContainer.setVisibility(View.GONE);
        setupCheckBoxListener();
        chargeDrinkList();
    }


    private void setupCheckBoxListener() {
        mDrinkSelectedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedDrinks = true;
                    mDrinkRecyclerViewContainer.setVisibility(View.VISIBLE);
                } else {
                    selectedDrinks = false;
                    mDrinkRecyclerViewContainer.setVisibility(View.GONE);
                    mSelectedDrinksAdapter = new DrinksAdapter(mDrinkListItem);
                    mDrinksRecyclerView.setAdapter(mSelectedDrinksAdapter);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ProviderSelectedActivity.class));
        finish();
    }

    public void getMainOrder() {
        try {
            String KEY_ACTIVITY = this.getIntent().getExtras().getString("KEY_ACTIVITY");
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            switch (KEY_ACTIVITY) {
                case "SELECTED_LUNCH":
                    if (bundle != null) {
                        mSelectedMenuObject = (Lunch) bundle.get(Constants.ACTION_SELECTED_MENU);
                        if (mSelectedMenuObject != null) {
                            mSelectedMainMenuTextView.setText("Plato principal: " + mSelectedMenuObject.getMainMenuDescription());
                            mSelectedGarnishTextView.setText("Guarnición: " + mSelectedMenuObject.getGarnishDescription());

                            String stringRaiting = String.valueOf(mSelectedMenuObject.getRaitingMenu());
                            float mRaitingValue = Float.parseFloat(stringRaiting);

                            switch (stringRaiting) {
                                case "1":
                                    mRatingMenu.setRating(mRaitingValue);
                                    mSelectedQualificationTextView.setText("Muy Malo");
                                    break;
                                case "2":
                                    mRatingMenu.setRating(mRaitingValue);
                                    mSelectedQualificationTextView.setText("Malo");
                                    break;
                                case "3":
                                    mRatingMenu.setRating(mRaitingValue);
                                    mSelectedQualificationTextView.setText("Bién");
                                    break;
                                case "4":
                                    mRatingMenu.setRating(mRaitingValue);
                                    mSelectedQualificationTextView.setText("Muy Bién");
                                    break;
                                case "5":
                                    mRatingMenu.setRating(mRaitingValue);
                                    mSelectedQualificationTextView.setText("Excelente");
                                    break;
                            }
                        }
                        break;
                    }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void chargeDrinkList() {
        mDrinkListItem = DrinksRepository.getAllDrinks();
        if (mDrinkListItem == null) {
            Utils.builToast(this, "Sin bebidas para mostrar");
        } else {
            mSelectedDrinksAdapter = new DrinksAdapter(mDrinkListItem);
            mDrinksRecyclerView.setAdapter(mSelectedDrinksAdapter);
        }
    }


    private void summaryLunchDialog() {

        int mTotalDrinkPrice = 0;
        int mTotalPrice = 0;
        summaryDialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        customeView = mlayoutInflater.inflate(R.layout.summary_dialog, null);
        TextView mMainMenuTextView = (TextView) customeView.findViewById(R.id.summary_main_menu_textView);
        TextView mGarnishTextView = (TextView) customeView.findViewById(R.id.summary_garnish_textView);
        TextView mTotalPriceTextView = (TextView) customeView.findViewById(R.id.summary_price_textView);
        RecyclerView mSummaryDrinkRecyclerView = (RecyclerView) customeView.findViewById(R.id.recyclerView_summary_drinks);
        AppCompatButton mCancelButton = (AppCompatButton) customeView.findViewById(R.id.action_cancel_button);
        AppCompatButton mSendButton = (AppCompatButton) customeView.findViewById(R.id.action_next_button);
        final SummaryDrinksAdapter mAdapterSummaryDrink;


        mSummaryDrinkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapterSummaryDrink = new SummaryDrinksAdapter(drinkReturnList);
        mSummaryDrinkRecyclerView.setAdapter(mAdapterSummaryDrink);
        mSummaryDrinkRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mSummaryDrinkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSummaryDrinkRecyclerView.setHasFixedSize(true);
        mSummaryDrinkRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mSummaryDrinkRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapterSummaryDrink.getItemAtPosition(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        mMainMenuTextView.setText("Menú principal: " + mSelectedMenuObject.getMainMenuDescription());
        mGarnishTextView.setText("Guarnición: " + mSelectedMenuObject.getGarnishDescription());

        for (Drinks dr : drinkReturnList) {
            mTotalDrinkPrice = mTotalDrinkPrice + dr.getPriceUnit();
        }

        mTotalPrice = mSelectedMenuObject.getPriceUnit() + mTotalDrinkPrice;

        mTotalPriceTextView.setText("Total de Consumición: " + mTotalPrice + "Gs.");

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkReturnList.clear();
                summaryDialog.dismiss();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        summaryDialog.setContentView(customeView);
        summaryDialog.setCanceledOnTouchOutside(false);
        summaryDialog.show();


    }

}