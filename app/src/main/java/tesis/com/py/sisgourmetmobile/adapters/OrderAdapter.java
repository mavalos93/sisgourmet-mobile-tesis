package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Order;
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


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderTypeTextView;
        TextView orderAmountTextView;
        TextView orderDateTextView;
        TextView orderStatusTextView;
        ImageView orderStatusIcon;
        ImageView orderTypeImageView;

        public OrderViewHolder(View view) {
            super(view);
            orderTypeTextView = (TextView) view.findViewById(R.id.item_order_type);
            orderAmountTextView = (TextView) view.findViewById(R.id.item_order_amount);
            orderDateTextView = (TextView) view.findViewById(R.id.item_order_date);
            orderStatusTextView = (TextView) view.findViewById(R.id.id_order_status);
            orderStatusIcon = (ImageView) view.findViewById(R.id.status_image_icon);
            orderTypeImageView = (ImageView) view.findViewById(R.id.order_type_imageView);
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
        String transactionType = "N/A";
        holder.orderTypeTextView.setText("Tipo: " + mOrder.getOrderType());
        holder.orderAmountTextView.setText("Monto total: " + mOrder.getOrderAmount());
        holder.orderDateTextView.setText("Fecha: " + Utils.dateFormatter(mOrder.getCreatedAt(), Constants.DEFAULT_DATETIME_FORMAT));
        holder.orderStatusTextView.setText("Estado: " + mOrder.getStatusDescription());


        if (mOrder.getStatusOrder() == Constants.TRANSACTION_NO_SEND) {
            iconResource = R.mipmap.ic_thumb_down_black_24dp;
            holder.orderStatusTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        } else {
            iconResource = R.mipmap.ic_thumb_up_black_24dp;
            holder.orderStatusTextView.setTextColor(mContext.getResources().getColor(R.color.accent));
        }
        holder.orderStatusIcon.setBackgroundResource(iconResource);

        switch (mOrder.getOrderType()) {
            case Constants.MAIN_MENU_ORDER:
                transactionType = mContext.getString(R.string.main_menu_order);
                iconOrder = R.mipmap.ic_restaurant_black_48dp;
                break;
            case Constants.GARNISH_ORDER:
                transactionType = mContext.getString(R.string.garnish_order);
                iconOrder = R.mipmap.ic_restaurant_black_48dp;
                break;
            case Constants.DRINK_ORDER:
                transactionType = mContext.getString(R.string.drink_order);
                iconOrder = R.mipmap.ic_local_drink_black_48dp;
                break;
            case Constants.LAUNCH_PACKAGE_ORDER:
                transactionType = mContext.getString(R.string.launch_package_order);
                iconOrder = R.mipmap.ic_restaurant_black_48dp;

        }
        holder.orderTypeTextView.setText(transactionType);
        holder.orderTypeImageView.setBackgroundResource(iconOrder);
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