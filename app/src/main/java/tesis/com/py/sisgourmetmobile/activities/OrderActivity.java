package tesis.com.py.sisgourmetmobile.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.SummaryDrinksAdapter;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class OrderActivity extends AppCompatActivity {

    public static final String TAG_CLASS = OrderActivity.class.getName();

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private AppCompatButton mSummaryButton;
    private AppCompatButton mSelectedLunchButton;
    private TextView mSelectedMainMenuTextView;
    private TextView mSelectedGarnishTextView;
    private TextView mSelectedQualificationTextView;
    private AppCompatRatingBar mRatingMenu;


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

        mSelectedMainMenuTextView = (TextView) findViewById(R.id.selected_main_menu_textView);
        mSelectedGarnishTextView = (TextView) findViewById(R.id.selected_garnish_textView);
        mSelectedQualificationTextView = (TextView) findViewById(R.id.selected_rating_description_textView);
        mRatingMenu = (AppCompatRatingBar) findViewById(R.id.selected_provider_rating_menu);
        mlayoutInflater = LayoutInflater.from(this);

        getMainOrder();


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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && data.getExtras() != null)
            for (String key : data.getExtras().keySet())
                Toast.makeText(this, key + " : " + data.getExtras().get(key).toString(), Toast.LENGTH_SHORT).show();

        super.onActivityResult(requestCode, resultCode, data);

    }





    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void getMainOrder() {
        try {
            String KEY_ACTIVITY = this.getIntent().getExtras().getString("KEY_ACTIVITY");
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            switch (KEY_ACTIVITY) {
                case "ADAPTER_ITEM_LUNCH":
                    if (bundle != null) {
                        mSelectedMenuObject = (Lunch) bundle.get(Constants.ACTION_SELECTED_MENU);
                        if (mSelectedMenuObject != null) {
                            mSelectedMainMenuTextView.setText("Plato principal: " + mSelectedMenuObject.getMainMenuDescription());
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




    private void summaryLunchDialog() {

        int mTotalDrinkPrice = 0;
        int mTotalPrice = 0;
        summaryDialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        customeView = mlayoutInflater.inflate(R.layout.summary_step, null);
        TextView mMainMenuTextView = (TextView) customeView.findViewById(R.id.summary_main_menu_textView);
        TextView mGarnishTextView = (TextView) customeView.findViewById(R.id.summary_garnish_textView);
        TextView mTotalPriceTextView = (TextView) customeView.findViewById(R.id.summary_price_textView);
        TextView mDrinkSelectedTextView = (TextView) customeView.findViewById(R.id.summary_drink_textView);
        RecyclerView mSummaryDrinkRecyclerView = (RecyclerView) customeView.findViewById(R.id.recyclerView_summary_drinks);
      /*  AppCompatButton mCancelButton = (AppCompatButton) customeView.findViewById(R.id.action_cancel_button);
        AppCompatButton mSendButton = (AppCompatButton) customeView.findViewById(R.id.action_next_button);*/
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
        mDrinkSelectedTextView.setText((drinkReturnList.size() == 0 ? "Sin bebidas seleccionadas": "Bebidas: "+drinkReturnList.size()));

        for (Drinks dr : drinkReturnList) {
            mTotalDrinkPrice = mTotalDrinkPrice + dr.getPriceUnit();
        }

        mTotalPrice = mSelectedMenuObject.getPriceUnit() + mTotalDrinkPrice;

        mTotalPriceTextView.setText("Total de Consumición: " + mTotalPrice + "Gs.");



        summaryDialog.setContentView(customeView);
        summaryDialog.setCanceledOnTouchOutside(false);
        summaryDialog.show();


    }

}
