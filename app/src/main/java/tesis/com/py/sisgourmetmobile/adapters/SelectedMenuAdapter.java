package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Lunch;

/**
 * Created by Manu0 on 16/4/2017.
 */

public class SelectedMenuAdapter extends RecyclerView.Adapter<SelectedMenuAdapter.SelectedMenuViewHolder> {
    private List<Lunch> mLunchList = new ArrayList<>();
    private Context mContext;
    SparseBooleanArray mSelectedItems = new SparseBooleanArray();

// When selecting an item, save it

    public SelectedMenuAdapter(List<Lunch> lunchList, Context context) {
        mLunchList = lunchList;
        mContext = context;
    }


    public class SelectedMenuViewHolder extends RecyclerView.ViewHolder {
        TextView mMainMenuTextView;
        TextView mGarnishTextView;
        TextView mQualificationTextView;
        TextView mPriceUnitTextView;
        AppCompatRatingBar mRaitingMenu;


        public SelectedMenuViewHolder(View view) {
            super(view);

            mMainMenuTextView = (TextView) view.findViewById(R.id.main_menu_textView);
            mGarnishTextView = (TextView) view.findViewById(R.id.garnish_textView);
            mPriceUnitTextView = (TextView) view.findViewById(R.id.price_unit_textView);
            mQualificationTextView = (TextView) view.findViewById(R.id.rating_description_textView);
            mRaitingMenu = (AppCompatRatingBar) view.findViewById(R.id.privider_rating_menu);

        }
    }

    @Override
    public SelectedMenuAdapter.SelectedMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lunch, parent, false);
        return new SelectedMenuAdapter.SelectedMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SelectedMenuViewHolder holder, final int position) {

        final Lunch mLunch = mLunchList.get(position);
        holder.mRaitingMenu.setClickable(false);
        holder.mMainMenuTextView.setText("Plato principal: " + mLunch.getMainMenuDescription());
        holder.mPriceUnitTextView.setText("Precio: "+ mLunch.getPriceUnit()+" Gs.");


        String stringRaiting = String.valueOf(mLunch.getRaitingMenu());
        float mRaitingValue = Float.parseFloat(stringRaiting);

        switch (stringRaiting) {
            case "1":
                holder.mRaitingMenu.setRating(mRaitingValue);
                holder.mQualificationTextView.setText("Muy Malo");
                break;
            case "2":
                holder.mRaitingMenu.setRating(mRaitingValue);
                holder.mQualificationTextView.setText("Malo");
                break;
            case "3":
                holder.mRaitingMenu.setRating(mRaitingValue);
                holder.mQualificationTextView.setText("Bién");
                break;
            case "4":
                holder.mRaitingMenu.setRating(mRaitingValue);
                holder.mQualificationTextView.setText("Muy Bién");
                break;
            case "5":
                holder.mRaitingMenu.setRating(mRaitingValue);
                holder.mQualificationTextView.setText("Excelente");
                break;
        }
    }


    @Override
    public long getItemId(int position) {
        return mLunchList.get(position).getId();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Lunch getItemAtPosition(int position) {
        return mLunchList.get(position);
    }

    @Override
    public int getItemCount() {
        return mLunchList.size();
    }

    public void setData(List<Lunch> data) {
        mLunchList = new ArrayList<>();
        mLunchList.addAll(data);
        notifyDataSetChanged();
    }

}