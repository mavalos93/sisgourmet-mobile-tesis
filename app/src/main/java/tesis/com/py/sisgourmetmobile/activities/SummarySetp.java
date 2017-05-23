package tesis.com.py.sisgourmetmobile.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import py.com.library.AbstractStep;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;

/**
 * Created by Manu0 on 22/5/2017.
 */

public class SummarySetp extends AbstractStep {

    private int i = 1;
    private Button button;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private LinearLayout mDrinkContainer;
    private Switch mNotDrinkSwitch;


    // Objects & variable
    private Lunch luncObject = new Lunch();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private boolean isDone = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.summary_step, null);



        return customeView;
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
        return "Resúmen";
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
        return "Tu resúmen ";
    }


    @Override
    public boolean nextIf() {
        // variable boolean to set done icon
        return true;
    }

    @Override
    public String error() {
        return "Error en la operación";
    }


}

