package tesis.com.py.sisgourmetmobile.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import py.com.library.AbstractStep;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;

/**
 * Created by Manu0 on 22/5/2017.
 */

public class SummaryStep extends AbstractStep {

    private int i = 3;
    private Button button;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private LinearLayout mDrinkContainer;
    private Switch mNotDrinkSwitch;
    private TextView mMainMenuTextView;
    private TextView mGarnishTextView;
    private TextView mTotalPriceTextView;
    private TextView mDrinkSelectedTextView;


    // Objects & variable
    private Lunch luncObject = new Lunch();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private boolean isDone = true;
    private int selection = StepDrinks.mSelectionId;
    private int mGarnishPrice;
    private int mLunchPrice;

    public SummaryStep(Lunch lunchObject) {
        this.luncObject = lunchObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.summary_step, null);
        mMainMenuTextView = (TextView) customeView.findViewById(R.id.summary_main_menu_textView);
        mGarnishTextView = (TextView) customeView.findViewById(R.id.summary_garnish_textView);
        mTotalPriceTextView = (TextView) customeView.findViewById(R.id.summary_price_textView);
        mDrinkSelectedTextView = (TextView) customeView.findViewById(R.id.summary_drink_textView);
        RecyclerView mSummaryDrinkRecyclerView = (RecyclerView) customeView.findViewById(R.id.recyclerView_summary_drinks);


        return customeView;
    }


    private void setupData(int mGarnishSelectedId, int mTypeLunch) {

        mMainMenuTextView.setText("Menú principal: " +luncObject.getMainMenuDescription());
        mLunchPrice = luncObject.getPriceUnit();

        switch (mTypeLunch) {
            case 1:
                Garnish mGarnihsQuery = GarnishRepository.getGarnishById(luncObject.getProviderId());
                if (mGarnihsQuery != null) {
                    mGarnishPrice = mGarnihsQuery.getUnitPrice();
                    mGarnishTextView.setText("Guarnición: "+mGarnihsQuery.getDescription());
                }

                break;
            case 2:
                Garnish mGarnihsQuerySelected = GarnishRepository.getGarnishById(mGarnishSelectedId);
                if (mGarnihsQuerySelected != null) {
                    mGarnishPrice = mGarnihsQuerySelected.getUnitPrice();
                    mGarnishTextView.setText("Guarnición: "+ mGarnihsQuerySelected.getDescription());
                }
                break;
        }

        mTotalPriceTextView.setText("Total: "+ String.valueOf(mLunchPrice + mGarnishPrice) + " Gs.");


    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(CLICK, i);
    }


    @Override
    public String name() {
        return "Resúmen";
    }

    @Override
    public boolean isOptional() {
        return isDone;
    }


    @Override
    public void onStepVisible() {
        Log.d("BEBIDAS", "VARIABLE_DRINKS: " + StepLunch.typeLunchCase);
        Log.d("MENU_PRINCIPAL", "VARIABLE_LUNCH: " + StepLunch.radioId);

        setupData(StepLunch.radioId,StepLunch.typeLunchCase);


    }

    @Override
    public void onNext() {
        System.out.println("onNext");
    }

    @Override
    public void onPrevious() {
        System.out.println("onPrevious");
    }

    @Override
    public String optional() {
        return "Tu resúmen ";
    }


    @Override
    public boolean nextIf() {
        return true;
    }

    @Override
    public String error() {
        return "Error en la operación";
    }


}

