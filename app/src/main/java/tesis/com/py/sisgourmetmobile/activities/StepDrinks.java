package tesis.com.py.sisgourmetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import py.com.library.AbstractStep;
import py.com.library.style.TabStepper;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.DrinksAdapter;
import tesis.com.py.sisgourmetmobile.adapters.SummaryDrinksAdapter;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 21/5/2017.
 */

public class StepDrinks extends AbstractStep {

    private int i = 1;
    private Button button;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private LinearLayout mDrinkContainer;


    // Objects & variable
    private Lunch luncObject = new Lunch();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private boolean isDone = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.selected_drinks_fragment, null);
        mDrinkContainer = (LinearLayout) customeView.findViewById(R.id.container_drinks);
        setupDataView();


        return customeView;
    }


    private void setupDataView() {

        List<Drinks> mDrinkList = DrinksRepository.getAllDrinks();
        if (mDrinkList.size() != 0) {
            for (Drinks drinks : mDrinkList) {
                CheckBox mDrinkCheckBox = new CheckBox(getContext());
                mDrinkCheckBox.setText(drinks.getDescription() + "\n" + "Precio: " + drinks.getPriceUnit() + " Gs.");
                mDrinkCheckBox.setId(drinks.getId().intValue());
                mDrinkCheckBox.setPadding(0, 20, 0, 20);
                mDrinkCheckBox.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_local_drink_black_48dp, 0, 0, 0);
                mDrinkContainer.addView(mDrinkCheckBox);
            }
        }
    }



    private void validationData() {


    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(CLICK, i);
    }


    @Override
    public String name() {
        return "Bebidas";
    }

    @Override
    public boolean isOptional() {
        // boolean method pass to next stepper
        return isDone;
    }


    @Override
    public void onStepVisible() {
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
        return "Selecciona tu bebida ";
    }


    @Override
    public boolean nextIf() {
        // variable boolean to set done icon
        return false;
    }

    @Override
    public String error() {
        return "Debes seleccionar una bebida";
    }


}

