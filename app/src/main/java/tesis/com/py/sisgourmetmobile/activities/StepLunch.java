package tesis.com.py.sisgourmetmobile.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
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
    private ImageView mProviderImage;
    private ImageView mMenuImageView;
    private AppCompatRatingBar mRatingMenu;
    private RadioGroup mRadioGroupGarnish;
    private LinearLayout mGarnishDataContainer;
    private TextView mQualificationValue;
    public static int radioGarnishId = 0;
    public static int typeLunchCase = 0;

    // Objects & variable
    private Lunch luncObject = new Lunch();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private boolean setupDoneIcon = false;


    public StepLunch(Lunch lunchObject) {
        this.luncObject = lunchObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.step_lunch_order, null);

        mSelectedMainMenuTextView = customeView.findViewById(R.id.selected_main_menu_textView);
        mSelectedGarnishTextView = customeView.findViewById(R.id.selected_garnish_textView);
        mMenuImageView = customeView.findViewById(R.id.menu_image);
        mRatingMenu = customeView.findViewById(R.id.selected_provider_rating_menu);
        mRadioGroupGarnish = customeView.findViewById(R.id.radioContainer);
        mQualificationValue = customeView.findViewById(R.id.menu_qualification_value);
        mGarnishDataContainer = customeView.findViewById(R.id.garnish_selected_container);
        mProviderImage = customeView.findViewById(R.id.provider_image);
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
            radioButton.setText(garnish.getDescription().toUpperCase() + "   |   " + Utils.formatNumber(String.valueOf(garnish.getUnitPrice()), " Gs."));
            radioButton.setId(garnish.getGarnishId());
            radioButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGraphite));
            radioButton.setPadding(10, 20, 10, 20);
            mRadioGroupGarnish.addView(radioButton);
        }
        mRadioGroupGarnish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioGarnishId = checkedId;
                Garnish mGarnihsQuery = GarnishRepository.getGarnishById(radioGarnishId);
                if (mGarnihsQuery != null) {
                    mSelectedGarnishTextView.setText(mGarnihsQuery.getDescription().toUpperCase());
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

        mQualificationValue.setText(stringRaiting);
        mRatingMenu.setRating(mRaitingValue);

        Provider provider = ProviderRepository.getProviderById(luncObject.getProviderId());
        if (provider != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(provider.getProviderImage(), 0, provider.getProviderImage().length);
            mProviderImage.setImageBitmap(bmp);
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(luncObject.getImageMenu(), 0, luncObject.getImageMenu().length);
        mMenuImageView.setImageBitmap(bmp);
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
