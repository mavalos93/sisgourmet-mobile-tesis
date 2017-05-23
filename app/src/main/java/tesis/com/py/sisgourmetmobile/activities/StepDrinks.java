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
    private Button button;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private LinearLayout mDrinkContainer;
    private Switch mNotDrinkSwitch;
    public static List<String> mSelectedDrinkItem = new ArrayList<>();


    // Objects & variable
    private boolean isDone = true;
    public static int mSelectionId = 0;
    private List<Drinks> mDrinkList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.selected_drinks_fragment, null);
        mDrinkContainer = (LinearLayout) customeView.findViewById(R.id.container_drinks);
        mNotDrinkSwitch = (Switch) customeView.findViewById(R.id.not_drink_switch);

        setupDataView();
        setupSwitchListener();

        return customeView;
    }

    private void setupSwitchListener() {
        mNotDrinkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDrinkContainer.setVisibility(View.GONE);
                } else {
                    mDrinkContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void setupDataView() {


        mDrinkList = DrinksRepository.getAllDrinks();
        if (mDrinkList.size() != 0) {
            for (Drinks drinks : mDrinkList) {
                final CheckBox mDrinkCheckBox = new CheckBox(getContext());
                mDrinkCheckBox.setText(drinks.getDescription() + "\n" + "Precio: " + drinks.getPriceUnit() + " Gs.");
                mDrinkCheckBox.setId(drinks.getId().intValue());
                mDrinkCheckBox.setPadding(0, 20, 0, 20);
                mDrinkCheckBox.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_local_drink_black_48dp, 0, 0, 0);
                mDrinkContainer.addView(mDrinkCheckBox);
            }
        }

        for (Drinks drinks : mDrinkList) {
            final CheckBox drinkCheckBox = (CheckBox) mDrinkContainer.findViewById(drinks.getId().intValue());
            drinkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mSelectionId = drinkCheckBox.getId();
                        mSelectedDrinkItem.add(String.valueOf(mSelectionId));
                    }
                }
            });
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
        mSelectedDrinkItem.clear();
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

