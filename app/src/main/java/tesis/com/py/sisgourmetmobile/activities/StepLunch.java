package tesis.com.py.sisgourmetmobile.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRatingBar;
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
import py.com.library.style.TabStepper;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
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
    public static int radioGarnishId = 0;
    public static int typeLunchCase = 0;
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
        customeView = mlayoutInflater.inflate(R.layout.step_lunch_order, null);

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

        mGarnishList = GarnishRepository.getGarnishByLunchId(luncObject.getPrincipalMenuCode());
        int listSize = mGarnishList.size();

        switch (listSize) {
            case 0:
                Utils.builToast(getContext(), "No se encontro guarnicion");
                break;
            case 1:
                setupDataRatingBar();
                typeLunchCase = 1;
                for (Garnish gr : mGarnishList) {
                    mSelectedGarnishTextView.setText(gr.getDescription());
                    radioGarnishId = gr.getGarnishId();
                }
                setupDoneIcon = true;
                TabStepper.isDone = true;
                break;
            default:
                typeLunchCase = 2;
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
            radioButton.setId(garnish.getGarnishId());
            radioButton.setPadding(0, 20, 0, 20);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_restaurant_menu_black_36dp, 0, 0, 0);
            mRadioGroupGarnish.addView(radioButton);
        }
        mRadioGroupGarnish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioGarnishId = checkedId;
                Garnish mGarnihsQuery = GarnishRepository.getGarnishById(radioGarnishId);
                if (mGarnihsQuery != null) {
                    mSelectedGarnishTextView.setText(mGarnihsQuery.getDescription());
                }

                TabStepper.isDone = true;
                setupDoneIcon = true;
            }
        });


    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(CLICK, i);
    }


    private void setupDataRatingBar() {
        String stringRaiting = String.valueOf(luncObject.getRatingMenu());
        float mRaitingValue = Float.parseFloat(stringRaiting);

        Log.d("tag", "rating: " + mRaitingValue);
        switch (stringRaiting) {
            case "1.0":
                mRatingMenu.setRating(mRaitingValue);
                mSelectedQualificationTextView.setText("Muy Malo");
                break;
            case "2.0":
                mRatingMenu.setRating(mRaitingValue);
                mSelectedQualificationTextView.setText("Malo");
                break;
            case "3.0":
                mRatingMenu.setRating(mRaitingValue);
                mSelectedQualificationTextView.setText("Bién");
                break;
            case "4.0":
                mRatingMenu.setRating(mRaitingValue);
                mSelectedQualificationTextView.setText("Muy Bién");
                break;
            case "5.0":
                mRatingMenu.setRating(mRaitingValue);
                mSelectedQualificationTextView.setText("Excelente");
                break;
        }

        Bitmap bmp = BitmapFactory.decodeByteArray(luncObject.getImageMenu(), 0, luncObject.getImageMenu().length);
        mProviderImageView.setImageBitmap(bmp);
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
