package tesis.com.py.sisgourmetmobile.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import py.com.library.AbstractStep;
import py.com.library.style.BaseStyle;
import py.com.library.style.TabStepper;
import py.com.library.util.LinearityChecker;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.SelectedMenuAdapter;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.fragments.MenuFragment;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;


/**
 * @author Francesco Cannizzaro (fcannizzaro).
 */
public class StepLunch extends AbstractStep {

    private int i = 1;
    private Button button;
    private final static String CLICK = "click";

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private TextView mSelectedMainMenuTextView;
    private TextView mSelectedGarnishTextView;
    private TextView mSelectedQualificationTextView;
    private ImageView mProviderImageView;
    private AppCompatRatingBar mRatingMenu;
    private RadioGroup mRadioGroupGarnish;
    private LinearLayout mGarnishDataContainer;
    private int radioId = 0;
    TabStepper tabStepper = new TabStepper();

    // Objects & variable
    private Lunch luncObject = new Lunch();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private boolean setupDoneIcon = false;
    private Provider mProviderObject = new Provider();


    public StepLunch(Lunch lunchObject) {
        this.luncObject = lunchObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.content_order, null);

        mSelectedMainMenuTextView = (TextView) customeView.findViewById(R.id.selected_main_menu_textView);
        mSelectedGarnishTextView = (TextView) customeView.findViewById(R.id.selected_garnish_textView);
        mSelectedQualificationTextView = (TextView) customeView.findViewById(R.id.selected_rating_description_textView);
        mProviderImageView = (ImageView) customeView.findViewById(R.id.provider_imageView);
        mRatingMenu = (AppCompatRatingBar) customeView.findViewById(R.id.selected_provider_rating_menu);
        mRadioGroupGarnish = (RadioGroup) customeView.findViewById(R.id.radioContainer);
        mGarnishDataContainer = (LinearLayout) customeView.findViewById(R.id.garnish_selected_container);
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
                setupDataRatingBar();
                for (Garnish dr : mGarnishList) {
                    mSelectedGarnishTextView.setText(dr.getDescription());
                }
                setupDoneIcon = true;
                TabStepper.isDone = true;
                break;
            default:
                setupDataRatingBar();
                chargeDataGarnish(mGarnishList);
                break;

        }
        mSelectedMainMenuTextView.setText(luncObject.getMainMenuDescription());


    }


    private void chargeDataGarnish(List<Garnish> garnishList) {
        for (Garnish garnish : garnishList) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(garnish.getDescription() + "\n" + "Precio: " + garnish.getUnitPrice() + " Gs.");
            radioButton.setId(garnish.getId().intValue());
            radioButton.setPadding(0, 20, 0, 20);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_restaurant_menu_black_36dp, 0, 0, 0);
            mRadioGroupGarnish.addView(radioButton);
        }
        mRadioGroupGarnish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.d("TAG_CLASS", "test: " + mRadioGroupGarnish.getCheckedRadioButtonId());
                radioId = checkedId;
                Garnish mGarnihsQuery = GarnishRepository.getGarnishById(radioId);
                if (mGarnihsQuery != null) {
                    mSelectedGarnishTextView.setText(mGarnihsQuery.getDescription());
                }

                TabStepper.isDone = true;
                setupDoneIcon = true;
            }
        });


    }

    private void validationData() {
        switch (mGarnishList.size()) {
            case 0:
                Utils.builToast(getContext(), "Error al pasar datos");
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
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(CLICK, i);
    }

    private void putSingleObject() {
        Bundle menuBundle = new Bundle();
        menuBundle.putSerializable(Constants.SINGLE_LUNCH_OBJECT, luncObject);
        Intent menuIntent = new Intent(getContext(), TabClassicSample.class);
        menuIntent.putExtra(Constants.SERIALIZABLE, menuBundle);
        getContext().startActivity(menuIntent);
    }

    private void setupDataRatingBar() {
        String stringRaiting = String.valueOf(luncObject.getRaitingMenu());
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

        mProviderObject = ProviderRepository.getProviderById(luncObject.getProviderId());

        if (mProviderObject != null) {
            switch (mProviderObject.getProviderName()) {
                case "La Vienesa":
                    mProviderImageView.setImageResource(R.mipmap.la_vienesa);
                    break;
                case "Ña Eustaquia":
                    mProviderImageView.setImageResource(R.mipmap.nha_esutaquia);
                    break;
                case "Bolsi":
                    mProviderImageView.setImageResource(R.mipmap.bolsi);
                    break;
            }
        }
    }

    @Override
    public String name() {
        return "Plato principal";
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
        // variable boolean to set done icon
        return setupDoneIcon;
    }

    @Override
    public boolean isOptional() {

        return false;
    }

    @Override
    public String error() {
        return "Debes seleccionar una guarnición";
    }


}