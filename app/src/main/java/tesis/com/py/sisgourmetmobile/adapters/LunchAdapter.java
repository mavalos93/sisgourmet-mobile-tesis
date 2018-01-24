package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
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
import tesis.com.py.sisgourmetmobile.activities.MainStepper;
import tesis.com.py.sisgourmetmobile.activities.QualificationActivity;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 1/24/2018.
 */

public class LunchAdapter extends RecyclerView.Adapter<LunchAdapter.LunchViewHolder> {

    private List<Lunch> mLunchItem = new ArrayList<>();
    private Lunch mLunchObject = new Lunch();
    private Context mContext;

    public LunchAdapter(List<Lunch> lunchList) {
        mLunchItem = lunchList;
    }

    public class LunchViewHolder extends RecyclerView.ViewHolder {
        private TextView mainMenuDescription;
        private ImageView itemImage;
        private TextView garnishDescription;
        private TextView priceDescription;
        private TextView mQualificationMenuValue;
        private ImageButton mQualificationButton;



        public LunchViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            this.mainMenuDescription = view.findViewById(R.id.main_menu_desciption);
            this.garnishDescription = view.findViewById(R.id.garnish_description);
            this.itemImage = view.findViewById(R.id.itemImage);
            this.mQualificationMenuValue = view.findViewById(R.id.qualification_menu_value);
            this.mQualificationButton = view.findViewById(R.id.star_imageButton);
            this.priceDescription = view.findViewById(R.id.price_description);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLunchObject = mLunchItem.get(getAdapterPosition());
                    Bundle menuBundle = new Bundle();
                    menuBundle.putSerializable(Constants.ACTION_SELECTED_MENU, mLunchObject);
                    Intent menuIntent = new Intent(mContext, MainStepper.class);
                    menuIntent.putExtra(Constants.SERIALIZABLE, menuBundle);
                    mContext.startActivity(menuIntent);
                }
            });

        }
    }

    @Override
    public LunchAdapter.LunchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_single_card, parent, false);
        return new LunchAdapter.LunchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LunchAdapter.LunchViewHolder holder, final int position) {

        final Lunch lunchItem = mLunchItem.get(position);
        String mGarnishText = "";
        int listSize;
        float mRatingValue;

        List<Garnish> garnisList = GarnishRepository.getGarnishByLunchId(lunchItem.getPrincipalMenuCode());

        listSize = garnisList.size();
        if (listSize != 0) {
            if (listSize == 1) {
                for (Garnish gr : garnisList) {
                    mGarnishText = gr.getDescription().toLowerCase();
                }
            } else if (listSize > 1) {
                mGarnishText = "Combinable";
                holder.garnishDescription.setTextColor(mContext.getResources().getColor(R.color.colorRatingbar));
            }
        }

        mRatingValue = Float.parseFloat(String.valueOf(lunchItem.getRatingMenu()));
        Bitmap bmp = BitmapFactory.decodeByteArray(lunchItem.getImageMenu(), 0, lunchItem.getImageMenu().length);
        holder.itemImage.setImageBitmap(bmp);

        holder.mainMenuDescription.setText(lunchItem.getMainMenuDescription().toLowerCase().trim());
        holder.garnishDescription.setText(mGarnishText);
        holder.mQualificationMenuValue.setText(String.valueOf(mRatingValue).replace(".", ","));
        holder.priceDescription.setText(Utils.formatNumber(String.valueOf(lunchItem.getPriceUnit())," Gs."));
        holder.mQualificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle menuBundle = new Bundle();
                menuBundle.putSerializable(Constants.ACTION_QUALIFICATION_MENU, lunchItem);
                Intent menuIntent = new Intent(mContext, QualificationActivity.class);
                menuIntent.putExtra(Constants.SERIALIZABLE, menuBundle);
                menuIntent.putExtra("KEY_ACTIVITY", "ACTION_QUALIFICATION_MENU");
                mContext.startActivity(menuIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLunchItem.size();
    }

    @Override
    public long getItemId(int position) {
        return mLunchItem.get(position).getId();
    }

    public Lunch getItemAtPosition(int position) {
        return mLunchItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<Lunch> data) {
        mLunchItem = new ArrayList<>();
        mLunchItem.addAll(data);
        notifyDataSetChanged();
    }


}



