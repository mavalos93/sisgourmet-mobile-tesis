package tesis.com.py.sisgourmetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatCheckBox;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
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

    private int i = 2;
    public static int mDrinkId = 0;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private RadioGroup mDrinkRadioContainer;
    private AppCompatCheckBox mNotDrinkCheckBox;


    // Objects & variable
    private boolean isDone = true;
    private List<Drinks> mDrinkList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.selected_drinks_fragment, null);
        mDrinkRadioContainer = (RadioGroup) customeView.findViewById(R.id.drink_radio_container);
        mNotDrinkCheckBox = (AppCompatCheckBox) customeView.findViewById(R.id.not_drink_checkBox);
        setupData();
        setupCheckBoxListener();
        return customeView;
    }

    private void setupData() {

        if (DrinksRepository.count() == 0) {
            Utils.builToast(getContext(), getString(R.string.error_not_data_drinks));
        } else {
            mDrinkList = DrinksRepository.getAllDrinks();
            setupDrinkView(mDrinkList, true);
            mDrinkRadioContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    Log.d("TAG_CLASS", "test: " + mDrinkRadioContainer.getCheckedRadioButtonId());
                    mDrinkId = checkedId;
                }
            });
        }

    }

    private void setupCheckBoxListener() {

        mNotDrinkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setupDrinkView(mDrinkList, false);
                } else {
                    setupDrinkView(mDrinkList, true);
                }
            }
        });

    }

    private void setupDrinkView(List<Drinks> mDrinkList, boolean isEnable) {
        mDrinkRadioContainer.removeAllViews();
        if (mDrinkList.size() != 0) {
            for (Drinks drinks : mDrinkList) {
                final RadioButton mDrinkRadioButton = new RadioButton(getContext());
                mDrinkRadioButton.setText(drinks.getDescription() + "\n" + "Precio: " + drinks.getPriceUnit() + " Gs.");
                mDrinkRadioButton.setId(drinks.getId().intValue());
                mDrinkRadioButton.setPadding(0, 20, 0, 20);
                mDrinkRadioButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.drink_icon, 0, 0, 0);
                mDrinkRadioButton.setEnabled(isEnable);
                mDrinkRadioContainer.addView(mDrinkRadioButton);
            }
        }
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
        return true;
    }

    @Override
    public String error() {
        return "Debes seleccionar una bebida";
    }


}

