package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.SummaryOrder;

/**
 * Created by Manu0 on 12/11/2017.
 */

public class SummaryOrderAdapter extends RecyclerView.Adapter<SummaryOrderAdapter.SummaryOrderViewHolder> {

    private List<SummaryOrder> mSummaryItem = new ArrayList<>();

    private Context mContext;

    public SummaryOrderAdapter(List<SummaryOrder> drinksList) {
        mSummaryItem = drinksList;
    }

    class SummaryOrderViewHolder extends RecyclerView.ViewHolder {

        private TextView mOrderDescription;
        private TextView mGarnishDescription;
        private TextView mDrinkDescription;
        private TextView mTotalAmount;
        private TextView mDateOrder;
        private ImageView mOrderImage;


        SummaryOrderViewHolder(View view) {
            super(view);
            mContext = view.getContext();

            mOrderDescription = view.findViewById(R.id.order_description);
            mGarnishDescription = view.findViewById(R.id.order_garnish);
            mDrinkDescription = view.findViewById(R.id.order_drink);
            mTotalAmount = view.findViewById(R.id.order_price);
            mDateOrder = view.findViewById(R.id.order_date);
            mOrderImage = view.findViewById(R.id.order_image);


        }
    }

    @Override
    public SummaryOrderAdapter.SummaryOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary_order, parent, false);
        return new SummaryOrderAdapter.SummaryOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SummaryOrderAdapter.SummaryOrderViewHolder holder, final int position) {

        SummaryOrder summaryOrder = mSummaryItem.get(position);

        holder.mOrderDescription.setText(summaryOrder.getOrderDescription());
        holder.mGarnishDescription.setText(summaryOrder.getGarnishDescription());
        holder.mDrinkDescription.setText(summaryOrder.getDrinkDescription());
        holder.mTotalAmount.setText(summaryOrder.getPrice());
        holder.mDateOrder.setText(summaryOrder.getDate());

        if (summaryOrder != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(summaryOrder.getImage(), 0, summaryOrder.getImage().length);
            holder.mOrderImage.setImageBitmap(bmp);
        }

    }

    public int getTotalSpendingOfMonth() {
        int mTotalAmount = 0;
        try {
            for (SummaryOrder so : mSummaryItem) {
                mTotalAmount = mTotalAmount + Integer.parseInt(so.getPrice());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mTotalAmount;
    }

    @Override
    public int getItemCount() {
        return mSummaryItem.size();
    }

    @Override
    public long getItemId(int position) {
        return mSummaryItem.get(position).getId();
    }

    public SummaryOrder getItemAtPosition(int position) {
        return mSummaryItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<SummaryOrder> data) {
        mSummaryItem = new ArrayList<>();
        mSummaryItem.addAll(data);
        notifyDataSetChanged();
    }

}



