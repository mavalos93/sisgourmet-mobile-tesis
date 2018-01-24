package tesis.com.py.sisgourmetmobile.activities;

import android.app.Dialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.library.AbstractStep;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.SummaryDrinksAdapter;
import tesis.com.py.sisgourmetmobile.dialogs.AlertDialogFragment;
import tesis.com.py.sisgourmetmobile.dialogs.CancelableAlertDialogFragment;
import tesis.com.py.sisgourmetmobile.dialogs.ProgressDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.entities.SummaryOrder;
import tesis.com.py.sisgourmetmobile.network.MyRequest;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.recivers.MyCommentsObserver;
import tesis.com.py.sisgourmetmobile.recivers.OrdersObserver;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.OrderRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;
import tesis.com.py.sisgourmetmobile.repositories.SummaryOrderRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.support.UserControlAmount;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.URLS;
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
    private TextView mMainMenuPrice;
    private TextView mGarnishTextView;
    private TextView mDrinkPrice;
    private TextView mGarnishPriceTextView;
    private TextView mTotalPriceTextView;
    private TextView mDrinkSelectedTextView;
    private TextView mProviderDescription;


    // Objects & variable
    private Lunch lunchObject = new Lunch();
    private int mGarnishPrice;
    private int mLunchPrice;
    private long mOrderId = 0;
    private String mTotalAmount;
    private OrderTask mOrderTask;
    private Order mOrder;

    // Dialogs
    private android.app.AlertDialog.Builder sendOrderDialog;


    // Request
    private RequestQueue mQueue;

    public SummaryStep(Lunch lunchObject) {
        this.lunchObject = lunchObject;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mlayoutInflater = LayoutInflater.from(getContext());
        customeView = mlayoutInflater.inflate(R.layout.summary_step, null);
        mMainMenuTextView = customeView.findViewById(R.id.selected_main_menu);
        mGarnishTextView = customeView.findViewById(R.id.selected_garnish_menu);
        mTotalPriceTextView = customeView.findViewById(R.id.total_amount);
        mDrinkSelectedTextView = customeView.findViewById(R.id.selected_drink);
        mProviderDescription = customeView.findViewById(R.id.provider_description);
        mMainMenuPrice = customeView.findViewById(R.id.main_menu_amount);
        mGarnishPriceTextView = customeView.findViewById(R.id.garnish_amount);
        mDrinkPrice = customeView.findViewById(R.id.drink_amount);


        return customeView;
    }


    private void setupData(int mGarnishSelectedId, int mTypeLunch, int mDrinkId) {

        int priceDrink = 0;

        if (mDrinkId == 0) {
            mDrinkSelectedTextView.setText("Sin Bebida");
            mDrinkPrice.setText(Utils.formatNumber(String.valueOf("0"), " Gs."));
        } else {
            Drinks mQueryDrink = DrinksRepository.getDrinkById(mDrinkId);
            if (mQueryDrink != null) {
                priceDrink = mQueryDrink.getPriceUnit();
                mDrinkSelectedTextView.setText(mQueryDrink.getDescription());
                mDrinkPrice.setText(Utils.formatNumber(String.valueOf(mQueryDrink.getPriceUnit()), " Gs."));

            }
        }


        mMainMenuTextView.setText(lunchObject.getMainMenuDescription().toLowerCase());
        mLunchPrice = lunchObject.getPriceUnit();
        mMainMenuPrice.setText(Utils.formatNumber(String.valueOf(lunchObject.getPriceUnit()), "Gs. "));

        switch (mTypeLunch) {

            case 1:
                List<Garnish> mGarnihsList = GarnishRepository.getGarnishByLunchId(lunchObject.getPrincipalMenuCode());
                if (mGarnihsList.size() != 0) {
                    for (Garnish gr : mGarnihsList) {
                        mGarnishPrice = gr.getUnitPrice();
                        mGarnishTextView.setText(gr.getDescription());
                        mGarnishPriceTextView.setText(Utils.formatNumber(String.valueOf(gr.getUnitPrice()), "Gs. "));
                    }
                }
                break;
            case 2:
                Garnish mGarnihsQuerySelected = GarnishRepository.getGarnishById(mGarnishSelectedId);
                if (mGarnihsQuerySelected != null) {
                    mGarnishPrice = mGarnihsQuerySelected.getUnitPrice();
                    mGarnishTextView.setText(mGarnihsQuerySelected.getDescription());
                    mGarnishPriceTextView.setText(Utils.formatNumber(String.valueOf(mGarnihsQuerySelected.getUnitPrice()), "Gs. "));

                }
                break;
        }

        mTotalAmount = String.valueOf(mLunchPrice + mGarnishPrice + priceDrink);
        mTotalPriceTextView.setText("Total: " + Utils.formatNumber(String.valueOf(mTotalAmount), " Gs."));
    }

    private void validateOrder(final Lunch lunchObject, final int mGarnishSelectedId, final int mDrinkId) {
        try {

            final String mUserName = UserRepository.getUser(getContext()).getUserName();
            if (lunchObject.getPrincipalMenuCode() == null || lunchObject.getPrincipalMenuCode() == 0) {
                Utils.builToast(getContext(), "Error, no se pudo obtener el identificador del menú principal");
                getActivity().finish();
                return;
            }

            if (lunchObject.getProviderId() == null || lunchObject.getProviderId() == 0) {
                Utils.builToast(getContext(), "Error, no se pudo obtener el identificador del proveedor");
                getActivity().finish();
                return;
            }

            if (mGarnishSelectedId == 0) {
                Utils.builToast(getContext(), "Error, no se pudo obtener el identificador de la guarnición");
                getActivity().finish();
                return;
            }

            if (mTotalAmount.isEmpty()) {
                Utils.builToast(getContext(), "Error, no se pudo realizar el cálculo del pedido");
                getActivity().finish();
                return;
            }

            if (UserControlAmount.overdaftAcount(getContext(), mTotalAmount)) {
                android.app.AlertDialog.Builder builder = Utils.createSimpleDialog(getContext(), "Sobregiro", "Ya sobrepasaste el tu monto asignado, deseas sobregirar el pedido ?", R.mipmap.ic_info_black_36dp, false);
                builder.setPositiveButton(getString(R.string.label_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (UserControlAmount.updateAmount(getContext(), mTotalAmount) <= 0) {
                            Utils.builToast(getContext(), getString(R.string.error_save_transaction));
                        } else {
                            mOrderTask = new OrderTask(mUserName, lunchObject.getProviderId(), lunchObject.getPrincipalMenuCode(),
                                    mGarnishSelectedId, mDrinkId, mTotalAmount);
                            mOrderTask.execute();
                        }
                    }
                });
                builder.setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
            } else {
                if (UserControlAmount.updateAmount(getContext(), mTotalAmount) <= 0) {
                    Utils.builToast(getContext(), getString(R.string.error_save_transaction));
                } else {
                    mOrderTask = new OrderTask(mUserName, lunchObject.getProviderId(), lunchObject.getPrincipalMenuCode(),
                            mGarnishSelectedId, mDrinkId, mTotalAmount);
                    mOrderTask.confirm();
                }
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
        return "Tú Resúmen";
    }

    @Override
    public boolean isOptional() {
        return true;
    }


    @Override
    public void onStepVisible() {
        setupData(StepLunch.radioGarnishId, StepLunch.typeLunchCase, StepDrinks.mDrinkId);

    }

    @Override
    public void onNext() {
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
        validateOrder(lunchObject, StepLunch.radioGarnishId, StepDrinks.mDrinkId);
        return true;
    }

    @Override
    public String error() {
        return "Error en la operación";
    }


    private class OrderTask extends MyRequest {
        static final String REQUEST_TAG = "OrderTask";
        private String mUserName;
        private int mProviderId;
        private int mLunchId;
        private Integer mGarnishId;
        private Integer mDrinkId;
        private String mTotalAmount;

        OrderTask(String mUserName, int mProviderId, int mLunchId, Integer mGarnishId, Integer mDrinkId, String mTotalAmount) {
            this.mUserName = mUserName;
            this.mProviderId = mProviderId;
            this.mLunchId = mLunchId;
            this.mGarnishId = mGarnishId;
            this.mDrinkId = mDrinkId;
            this.mTotalAmount = mTotalAmount;
        }

        @Override
        protected void confirm() {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setIcon(R.mipmap.ic_info_black_36dp);
            builder.setTitle(R.string.dialog_confirmation_title);
            builder.setMessage(R.string.dialog_confirmation_message);
            builder.setPositiveButton(R.string.label_accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    execute();
                }
            });
            builder.setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mOrderTask = null;
                }
            });
            android.app.AlertDialog confirmDialog = builder.create();
            confirmDialog.setCanceledOnTouchOutside(false);
            confirmDialog.show();
        }

        @Override
        protected void execute() {

            progressDialog = ProgressDialogFragment.newInstance(getContext());
            progressDialog.show(getActivity().getFragmentManager(), ProgressDialogFragment.TAG_CLASS);

            if (jsonObjectRequest != null)
                mQueue.cancelAll(REQUEST_TAG);

            Gson mGsonObject = new Gson();
            final OrderRequest mOrderRequest = new OrderRequest(mUserName, mProviderId, mLunchId, mGarnishId, mDrinkId, mTotalAmount);
            if (mOrder == null) {
                mOrder = new Order();
                mOrder.setOrderType(Constants.LUNCH_PACKAGE_ORDER);
                mOrder.setStatusOrder(Constants.TRANSACTION_NO_SEND);
                mOrder.setLunchId(Long.valueOf(String.valueOf(mLunchId)));
                mOrder.setDrinkId(mDrinkId);
                mOrder.setGarnishId(mGarnishId);
                mOrder.setCreatedAt(Utils.getToday().getTime());
                mOrder.setSendAppAt(Utils.formatDate(new Date(), Constants.DEFAULT_DATE_FORMAT_POSTGRES));
                mOrder.setProviderId(Long.valueOf(String.valueOf(mProviderId)));
                mOrder.setOrderAmount(mTotalAmount);
                mOrder.setRatingLunch(0L);
                mOrder.setUser(mUserName);
                mOrder.setHttpDetail(String.valueOf(mOrderRequest.getParams()));
                mOrderId = OrderRepository.store(mOrder);

                try {
                    if (mOrderId <= 0) {
                        Utils.builToast(getContext(), getString(R.string.error_save_transaction));
                        progressDialog.dismiss();
                        return;
                    } else {
                        getActivity().sendBroadcast(new Intent(OrdersObserver.ACTION_LOAD_ORDERS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }


            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URLS.ORDER_URL,
                    mOrderRequest.getParams(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            handleResponse(response);
                            jsonObjectRequest.cancel();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            String message = NetworkQueue.handleError(error, getContext());
                            OrderRepository.updateOrderTransaction(getContext(), mOrder.getId(), 0, Constants.TRANSACTION_NO_SEND, message);
                            jsonObjectRequest.cancel();
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                            builder.setIcon(R.mipmap.ic_info_black_36dp);
                            builder.setTitle(R.string.dialog_error_title);
                            builder.setMessage(message);
                            builder.setPositiveButton(R.string.label_retry, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    execute();
                                }
                            });
                            builder.setNegativeButton(R.string.label_send_queue, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOrderTask = null;
                                    Utils.builToast(getContext(), "Pedido marcado como pendiente");
                                    getActivity().finish();
                                }
                            });
                            android.app.AlertDialog confirmDialog = builder.create();
                            confirmDialog.setCanceledOnTouchOutside(false);
                            confirmDialog.show();
                        }
                    });
            jsonObjectRequest.setRetryPolicy(NetworkQueue.getDefaultRetryPolicy());
            jsonObjectRequest.setTag(REQUEST_TAG);
            Log.v(REQUEST_TAG, "Queueing: " + mGsonObject.toJson(mOrderRequest.getParams()));
            mQueue = Volley.newRequestQueue(getContext());
            mQueue.add(jsonObjectRequest);
            mQueue.start();

        }

        @Override
        protected void handleResponse(JSONObject response) {

            String message = null;
            int status = -1;
            int mTransactionOrderId = 0;

            if (response == null) {
                OrderRepository.updateOrderTransaction(getContext(), mOrder.getId(), 0, Constants.TRANSACTION_NO_SEND, "Error inesperado,intente de nuevo");
                Utils.builToast(getContext(), getString(R.string.volley_parse_error));
                getActivity().finish();
                return;
            }

            Log.i(TAG_CLASS, REQUEST_TAG + " | Response: " + response.toString());

            try {
                if (response.has("status")) status = response.getInt("status");
                if (response.has("message")) message = response.getString("message");
                if (response.has("order_id")) mTransactionOrderId = response.getInt("order_id");


                if (status != Constants.RESPONSE_OK) {
                    OrderRepository.updateOrderTransaction(getContext(), mOrder.getId(), 0, Constants.TRANSACTION_NO_SEND, message);
                    Utils.builToast(getContext(), message);
                    return;
                }
                UserControlAmount.saveHistoryData(mOrderId, Utils.formatDate(new Date(), Constants.DEFAULT_DATE_FORMAT), mDrinkId, mLunchId, mGarnishId, mProviderId, mTotalAmount);
                OrderRepository.updateOrderTransaction(getContext(), mOrder.getId(), mTransactionOrderId, Constants.TRANSACTION_SEND, message);
                mOrder = null;

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setIcon(R.mipmap.ic_done_black_36dp);
                builder.setTitle(R.string.dialog_success_title);
                builder.setMessage(message);
                builder.setPositiveButton(R.string.label_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

                android.app.AlertDialog doneDialog = builder.create();
                doneDialog.setCanceledOnTouchOutside(false);
                doneDialog.show();

            } catch (JSONException e) {
                e.printStackTrace();
                Utils.builToast(getContext(), getString(R.string.error_parsing_json));
            }
        }

        class OrderRequest extends RequestObject {
            private final String TAG_CLASS = OrderRequest.class.getName();

            private String mUsername;
            private String mIndifyCard;
            private long mProviderId;
            private long mLunchId;
            private int mGarnishId;
            private int mDrinkId;
            private String mTotalAmount;

            OrderRequest(String username, long providerId, long lunchId, int garnishId, int drinkId, String totalAmount) {
                mUsername = username;
                mIndifyCard = UserRepository.getUser(getContext()).getIdentifyCard();
                mProviderId = providerId;
                mLunchId = lunchId;
                mGarnishId = garnishId;
                mDrinkId = drinkId;
                mTotalAmount = totalAmount;

            }

            @Override
            public JSONObject getParams() {
                JSONObject params = new JSONObject();

                //TODO CONTROLAR EL PARSE DE STRING A DOUBLE
                try {
                    params.put("username", mUsername);
                    params.put("identify_card", mIndifyCard);
                    params.put("provider_id", mProviderId);
                    params.put("main_menu_id", mLunchId);
                    params.put("garnish_id", mGarnishId);
                    params.put("drink_id", mDrinkId);
                    params.put("total_amount", Double.parseDouble(mTotalAmount));
                    params.put("send_app_at", Utils.formatDate(new Date(), Constants.DEFAULT_DATE_FORMAT_POSTGRES));
                } catch (JSONException jEX) {
                    Log.w(TAG_CLASS, "Error while create JSONObject " + jEX.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return params;
            }
        }
    }


}

