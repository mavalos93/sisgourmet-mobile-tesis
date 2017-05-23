package tesis.com.py.sisgourmetmobile.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import py.com.library.AbstractStep;
import py.com.library.style.BaseStyle;
import py.com.library.style.TabStepper;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;


public class TabClassicSample extends TabStepper {

    private int i = 1;
    private Lunch lunchObject = new Lunch();
    private long mProviderId = 0;
    private Toolbar mToolbar;
    private BaseStyle mBaseStyle = new BaseStyle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setErrorTimeout(1500);
        setLinear(false);
        setTitle("Mi Almuerzo");
        setAlternativeTab(false);
        setDisabledTouch();
        setPreviousVisible();
        setStartPreviousButton();
        getLunchObject();

        addStep(createFragment(new StepLunch(lunchObject)));
        addStep(createFragment(new StepDrinks()));
        addStep(createFragment(new SummarySetp()));
        super.onCreate(savedInstanceState);
    }

    private AbstractStep createFragment(AbstractStep fragment) {
        Bundle b = new Bundle();
        b.putInt("position", i++);
        fragment.setArguments(b);
        return fragment;
    }

    public void getLunchObject() {
        try {
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            if (bundle != null) {
                lunchObject = (Lunch) bundle.get(Constants.ACTION_SELECTED_MENU);
                if (lunchObject != null) {
                    Log.d("LUNCH_TAG", "LUNCH: " + lunchObject.toString());
                    mProviderId = lunchObject.getProviderId();
                } else {
                    Utils.builToast(this, getString(R.string.error_get_lunch_object));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
