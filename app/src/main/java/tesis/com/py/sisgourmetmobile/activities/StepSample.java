package tesis.com.py.sisgourmetmobile.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import py.com.library.AbstractStep;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.SelectedMenuAdapter;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.fragments.MenuFragment;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;


/**
 * @author Francesco Cannizzaro (fcannizzaro).
 */
public class StepSample extends AbstractStep {

    private int i = 1;
    private Button button;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private TextView mSelectedMainMenuTextView;
    private TextView mSelectedGarnishTextView;
    private TextView mSelectedQualificationTextView;
    private AppCompatRatingBar mRatingMenu;

    // Objects & variable
    private Lunch luncObject = new Lunch();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private Lunch mSelectedMenuObject = new Lunch();



    public StepSample(Lunch lunchObject) {
        this.luncObject = lunchObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.content_order, null);

        mSelectedMainMenuTextView = (TextView) customeView.findViewById(R.id.selected_main_menu_textView);
        mSelectedGarnishTextView = (TextView) customeView.findViewById(R.id.selected_garnish_textView);
        mSelectedQualificationTextView = (TextView) customeView.findViewById(R.id.selected_rating_description_textView);
        mRatingMenu = (AppCompatRatingBar) customeView.findViewById(R.id.selected_provider_rating_menu);


        setupDataView();

        return customeView;
    }


    private void setupDataView() {

        mGarnishList = GarnishRepository.getGarnishByLunchId(luncObject.getId());
        int listSize = mGarnishList.size();

        switch (listSize) {
            case 0:
                Utils.builToast(getContext(), "No se encontro guarnicion");
                break;
            case 1:
                for (Garnish dr : mGarnishList) {
                    mSelectedGarnishTextView.setText(dr.getDescription());
                }
                break;
            default:
                chargeDataGarnish(mGarnishList);
                break;

        }
        mSelectedMainMenuTextView.setText(luncObject.getMainMenuDescription());


    }



    private void chargeDataGarnish(List<Garnish> garnishList){




    }
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(CLICK, i);

    }

    @Override
    public String name() {
        return "Plato principal";
    }

    @Override
    public boolean isOptional() {
        return true;
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
        return "Configura tu menú";
    }

    @Override
    public boolean nextIf() {
        return i > 1;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }
}
