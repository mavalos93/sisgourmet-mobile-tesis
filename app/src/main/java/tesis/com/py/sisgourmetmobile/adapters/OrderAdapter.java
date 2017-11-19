package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.QualificationActivity;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by mavalos on 23/02/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> mOrderList = new ArrayList<>();
    private Context mContext;

    public OrderAdapter(List<Order> orderList, Context context) {
        mOrderList = orderList;
        mContext = context;
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderAmountTextView;
        TextView orderStatusTextView;
        TextView ratingValueTextView;
        TextView mSendAppAtTextView;
        ImageView orderStatusIcon;
        ImageView orderTypeImageView;
        ImageButton starCommetButton;
        ImageButton detailsButton;

        OrderViewHolder(View view) {
            super(view);
            orderAmountTextView = view.findViewById(R.id.item_order_amount);
            orderStatusTextView = view.findViewById(R.id.id_order_status);
            orderStatusIcon = view.findViewById(R.id.status_image_icon);
            orderTypeImageView = view.findViewById(R.id.order_imageView);
            ratingValueTextView = view.findViewById(R.id.item_rating_value_textView);
            starCommetButton = view.findViewById(R.id.star_comment_button);
            mSendAppAtTextView = view.findViewById(R.id.date_order_textView);
            detailsButton = view.findViewById(R.id.details_button);
            starCommetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        sendOrderObject(mOrderList, getAdapterPosition());

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailsOrder(getItemAtPosition(getAdapterPosition()));
                }
            });

        }
    }

    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        return new OrderAdapter.OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderViewHolder holder, int position) {
        Order mOrder = mOrderList.get(position);
        int iconResource;
        int iconOrder = 0;
        String mStatusOrder = "";
        holder.orderAmountTextView.setText(Utils.formatNumber(mOrder.getOrderAmount()," Gs."));
        holder.mSendAppAtTextView.setText(mOrder.getSendAppAt());


        if (mOrder.getStatusOrder() == Constants.TRANSACTION_NO_SEND) {
            iconResource = R.mipmap.ic_replay_black_24dp;
            holder.orderStatusTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            mStatusOrder = "pendiente";
        } else {
            iconResource = R.mipmap.ic_done_black_24dp;
            holder.orderStatusTextView.setTextColor(mContext.getResources().getColor(R.color.colorOk));
            mStatusOrder = "enviado";
        }
        holder.orderStatusIcon.setBackgroundResource(iconResource);
        Lunch mLunchObject = LunchRepository.getLunchById(mOrder.getLunchId());


        if (mLunchObject != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(mLunchObject.getImageMenu(), 0, mLunchObject.getImageMenu().length);
            holder.orderTypeImageView.setImageBitmap(bmp);
        }

        if (mOrder.getRatingLunch() == null || mOrder.getRatingLunch() == 0) {
            holder.ratingValueTextView.setText("0.0");
        } else {
            String stringRaiting = String.valueOf(mOrder.getRatingLunch());

            switch (stringRaiting) {
                case "1":
                    holder.ratingValueTextView.setText("1.0");
                    break;
                case "2":
                    holder.ratingValueTextView.setText("2.0");

                    break;
                case "3":
                    holder.ratingValueTextView.setText("3.0");

                    break;
                case "4":
                    holder.ratingValueTextView.setText("4.0");

                    break;
                case "5":
                    holder.ratingValueTextView.setText("5.0");

                    break;
            }
        }
        holder.orderStatusTextView.setText(mStatusOrder);
        holder.orderTypeImageView.setBackgroundResource(iconOrder);
    }

    private void detailsOrder(Order orderObject) {
        String mSelectedDrink = "SIN BEBIDA";
        String mSelectedMainMenu = "";
        String mSelectedGarnish = "";
        String mSelectedProvider = "";
        String mRatingValue = "SIN CALIFICAR";

        if (orderObject.getDrinkId() != 0) {
            Drinks mDrinkQuery = DrinksRepository.getDrinkById(orderObject.getDrinkId());
            if (mDrinkQuery != null) {
                mSelectedDrink = mDrinkQuery.getDescription();
            }
        }
        if (orderObject.getLunchId() != 0) {
            Lunch mLunchQuery = LunchRepository.getLunchById(orderObject.getLunchId());
            if (mLunchQuery != null) {
                mSelectedMainMenu = mLunchQuery.getMainMenuDescription();
            }
        }

        if (orderObject.getGarnishId() != 0) {
            Garnish mGarnishQuery = GarnishRepository.getGarnishById(orderObject.getGarnishId());
            if (mGarnishQuery != null) {
                mSelectedGarnish = mGarnishQuery.getDescription();
            }
        }

        if (orderObject.getProviderId() != 0) {
            Provider mProviderQuery = ProviderRepository.getProviderById(orderObject.getProviderId());
            if (mProviderQuery != null) {
                mSelectedProvider = mProviderQuery.getProviderName();
            }
        }

        if (orderObject.getRatingLunch() != null) {
            mRatingValue = String.valueOf(orderObject.getRatingLunch());
        }


        StringBuilder sb = new StringBuilder();
        sb.append("TIPO: ")
                .append(orderObject.getOrderType())
                .append("\n")
                .append("FECHA/HORA-ENVIO: ")
                .append(orderObject.getSendAppAt())
                .append("\n")
                .append("MENU PRINCIPAL: ")
                .append(mSelectedMainMenu)
                .append("\n")
                .append("GUARNICION: ")
                .append(mSelectedGarnish)
                .append("\n")
                .append("BEBIDAS: ")
                .append(mSelectedDrink)
                .append("\n")
                .append("PROVEEDOR: ")
                .append(mSelectedProvider)
                .append("\n")
                .append("CALIFICACION: ")
                .append(mRatingValue);
        android.app.AlertDialog.Builder builder = Utils.createSimpleDialog(mContext, mContext.getString(R.string.order_dialog_title), sb.toString(), R.mipmap.ic_thumb_up_black_24dp, false);
        builder.setPositiveButton(mContext.getString(R.string.label_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();

    }

    private void sendOrderObject(List<Order> orderList, int position) {
        Bundle menuBundle = new Bundle();
        menuBundle.putSerializable(Constants.SEND_ORDER_OBJECT, orderList.get(position));
        Intent menuIntent = new Intent(mContext, QualificationActivity.class);
        menuIntent.putExtra(Constants.SERIALIZABLE, menuBundle);
        menuIntent.putExtra("KEY_ACTIVITY", "SEND_ORDER_OBJECT");
        mContext.startActivity(menuIntent);
    }

    @Override
    public long getItemId(int position) {
        return mOrderList.get(position).getId();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Order getItemAtPosition(int position) {
        return mOrderList.get(position);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public void setData(List<Order> data) {
        mOrderList = new ArrayList<>();
        mOrderList.addAll(data);
        notifyDataSetChanged();
    }

}