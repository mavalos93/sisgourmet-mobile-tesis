package tesis.com.py.sisgourmetmobile.activities;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.library.AbstractStep;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.SummaryDrinksAdapter;
import tesis.com.py.sisgourmetmobile.dialogs.AlertDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.recivers.OrdersObserver;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.OrderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 22/5/2017.
 */

public class SummaryStep extends AbstractStep {

    private int i = 3;
    private final static String CLICK = "click";
    private final String TAG_CLASS = SummaryStep.class.getName();

    // View
    private LayoutInflater mlayoutInflater;
    private View customeView;
    private TextView mMainMenuTextView;
    private TextView mGarnishTextView;
    private TextView mTotalPriceTextView;
    private TextView mDrinkSelectedTextView;
    RecyclerView mSummaryDrinkRecyclerView;


    // Objects & variable
    private Lunch lunchObject = new Lunch();
    private boolean isDone = true;
    private int mGarnishPrice;
    private int mLunchPrice;
    private List<Drinks> selectedDrinkList = new ArrayList<>();
    private SummaryDrinksAdapter mAdapterSummaryDrink;
    private String mTotalPrice;

    // Dialogs
    private android.app.AlertDialog.Builder sendOrderDialog;

    public SummaryStep(Lunch lunchObject) {
        this.lunchObject = lunchObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.summary_step, null);
        mMainMenuTextView = (TextView) customeView.findViewById(R.id.summary_main_menu_textView);
        mGarnishTextView = (TextView) customeView.findViewById(R.id.summary_garnish_textView);
        mTotalPriceTextView = (TextView) customeView.findViewById(R.id.summary_price_textView);
        mDrinkSelectedTextView = (TextView) customeView.findViewById(R.id.summary_drink_textView);
        mSummaryDrinkRecyclerView = (RecyclerView) customeView.findViewById(R.id.recyclerView_summary_drinks);


        mSummaryDrinkRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSummaryDrinkRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mSummaryDrinkRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSummaryDrinkRecyclerView.setHasFixedSize(true);
        mSummaryDrinkRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mSummaryDrinkRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapterSummaryDrink.getItemAtPosition(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));


        return customeView;
    }


    private void setupData(int mGarnishSelectedId, int mTypeLunch, List<String> mDrinkSelectedList) {

        int acumPriceDrink = 0;

        if (mDrinkSelectedList.size() != 0) {
            for (int position = 0; position < mDrinkSelectedList.size(); position++) {
                Drinks mQueryDrink = DrinksRepository.getDrinkById(Long.valueOf(mDrinkSelectedList.get(position)));
                if (mQueryDrink != null) {
                    acumPriceDrink = acumPriceDrink + mQueryDrink.getPriceUnit();
                    selectedDrinkList.add(mQueryDrink);
                    mAdapterSummaryDrink.setData(selectedDrinkList);
                    mAdapterSummaryDrink.notifyDataSetChanged();
                }
            }
        }

        mMainMenuTextView.setText("Menú principal: " + lunchObject.getMainMenuDescription());
        mLunchPrice = lunchObject.getPriceUnit();


        switch (mTypeLunch) {
            case 1:
                Garnish mGarnihsQuery = GarnishRepository.getGarnishById(lunchObject.getProviderId());
                if (mGarnihsQuery != null) {
                    mGarnishPrice = mGarnihsQuery.getUnitPrice();
                    mGarnishTextView.setText("Guarnición: " + mGarnihsQuery.getDescription());
                }

                break;
            case 2:
                Garnish mGarnihsQuerySelected = GarnishRepository.getGarnishById(mGarnishSelectedId);
                if (mGarnihsQuerySelected != null) {
                    mGarnishPrice = mGarnihsQuerySelected.getUnitPrice();
                    mGarnishTextView.setText("Guarnición: " + mGarnihsQuerySelected.getDescription());
                }
                break;
        }

        mTotalPrice = String.valueOf(mLunchPrice + mGarnishPrice + acumPriceDrink) + " Gs.";
        mTotalPriceTextView.setText("Total: " + mTotalPrice);
    }

    private void saveOrder(Lunch lunchObject, int mGarnishSelectedId, List<String> mDrinkSelectedList) {
        try {
            long mOrderId = 0;
            String mDrinksDetails = String.valueOf(mDrinkSelectedList);
            Order orderObject = new Order();
            orderObject.setOrderType(Constants.LUNCH_PACKAGE_ORDER);
            orderObject.setStatusOrder(Constants.TRANSACTION_SEND);
            orderObject.setSelectedDrinks(mDrinksDetails);
            orderObject.setLunchId(lunchObject.getId());
            orderObject.setGarnishId(mGarnishSelectedId);
            orderObject.setSendAppAt(Utils.formatDate(new Date(), Constants.DEFAULT_DATETIME_FORMAT));
            orderObject.setCreatedAt(Utils.getToday().getTime());
            orderObject.setOrderAmount(mTotalPrice);
            orderObject.setProviderId(lunchObject.getProviderId());
            mOrderId = OrderRepository.store(orderObject);


            if (mOrderId > 0) {
                sendOrderDialog = Utils.createSimpleDialog(getContext(),
                        getString(R.string.my_lunch),
                        getString(R.string.order_send_succes_message),
                        R.mipmap.ic_done_black_36dp, false);
                sendOrderDialog.setPositiveButton(getString(R.string.label_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        getActivity().sendBroadcast(new Intent(OrdersObserver.ACTION_LOAD_ORDERS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    }
                });
                sendOrderDialog.create();
                sendOrderDialog.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(CLICK, i);
    }


    @Override
    public String name() {
        Log.d("TAG", "NAME");
        return "Tú Resúmen";
    }

    @Override
    public boolean isOptional() {
        return isDone;
    }


    @Override
    public void onStepVisible() {
        selectedDrinkList.clear();
        mAdapterSummaryDrink = new SummaryDrinksAdapter(new ArrayList<Drinks>());
        mSummaryDrinkRecyclerView.setAdapter(mAdapterSummaryDrink);
        setupData(StepLunch.radioGarnishId, StepLunch.typeLunchCase, StepDrinks.mSelectedDrinkItem);

    }

    @Override
    public void onNext() {
        Log.d("TAG", "onNext");
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
        saveOrder(lunchObject, StepLunch.radioGarnishId, StepDrinks.mSelectedDrinkItem);
        return true;
    }

    @Override
    public String error() {
        return "Error en la operación";
    }


}

