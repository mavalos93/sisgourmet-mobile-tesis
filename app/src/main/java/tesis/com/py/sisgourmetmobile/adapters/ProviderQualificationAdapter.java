package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.ProviderRating;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 9/10/2017.
 */

public class ProviderQualificationAdapter extends RecyclerView.Adapter<ProviderQualificationAdapter.ProviderQualificationViewHolder> {

    private List<ProviderRating> providerQualificationItem = new ArrayList<>();
    private Context mContext;


    public ProviderQualificationAdapter(List<ProviderRating> ProviderRatingList, Context context) {
        providerQualificationItem = ProviderRatingList;
        mContext = context;
    }

    class ProviderQualificationViewHolder extends RecyclerView.ViewHolder {

        TextView mProviderNameTextView;
        TextView mTotalRatingTextView;
        ImageView mProviderImage;
        AppCompatRatingBar mProviderRatingBar;
        TextView mTotalCommentsTextView;
        ProgressBar mFiveRatingProgressBar;
        ProgressBar mFourRatingProgressBar;
        ProgressBar mThreeRatingProgressBar;
        ProgressBar mTwoRatingProgressBar;
        ProgressBar mOneRatingProgressBar;


        ProviderQualificationViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mProviderNameTextView = view.findViewById(R.id.provider_name);
            mTotalRatingTextView = view.findViewById(R.id.provider_rating_value);
            mProviderImage = view.findViewById(R.id.provider_image);
            mProviderRatingBar = view.findViewById(R.id.qualification_rating_bar);
            mTotalCommentsTextView = view.findViewById(R.id.total_user_comments_textView);
            mFiveRatingProgressBar = view.findViewById(R.id.five_rating_start);
            mFourRatingProgressBar = view.findViewById(R.id.four_rating_start);
            mThreeRatingProgressBar = view.findViewById(R.id.three_rating_start);
            mTwoRatingProgressBar = view.findViewById(R.id.two_rating_start);
            mOneRatingProgressBar = view.findViewById(R.id.one_rating_start);


        }
    }

    @Override
    public ProviderQualificationAdapter.ProviderQualificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_provider_value, parent, false);
        return new ProviderQualificationAdapter.ProviderQualificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProviderQualificationAdapter.ProviderQualificationViewHolder holder, final int position) {
        ProviderRating providerRating = getItemAtPosition(position);

        holder.mProviderNameTextView.setText(providerRating.getProviderName());
        holder.mTotalRatingTextView.setText(providerRating.getProviderRating().replace(".", ","));
        Bitmap bmp = BitmapFactory.decodeByteArray(providerRating.getProviderImage(), 0, providerRating.getProviderImage().length);
        holder.mProviderImage.setImageBitmap(bmp);
        holder.mTotalCommentsTextView.setText(String.valueOf(providerRating.getTotalUserComments()));
        holder.mProviderRatingBar.setRating(Utils.setupRatingValue(providerRating.getProviderRating()));


        holder.mFiveRatingProgressBar.setMax(providerRating.getTotalUserComments());
        holder.mFiveRatingProgressBar.setProgress(providerRating.getFiveStar());

        holder.mFourRatingProgressBar.setMax(providerRating.getTotalUserComments());
        holder.mFourRatingProgressBar.setProgress(providerRating.getFourStar());


        holder.mThreeRatingProgressBar.setMax(providerRating.getTotalUserComments());
        holder.mThreeRatingProgressBar.setProgress(providerRating.getThreeStar());

        holder.mTwoRatingProgressBar.setMax(providerRating.getTotalUserComments());
        holder.mTwoRatingProgressBar.setProgress(providerRating.getTwoStar());

        holder.mOneRatingProgressBar.setMax(providerRating.getTotalUserComments());
        holder.mOneRatingProgressBar.setProgress(providerRating.getOneStar());

    }




    @Override
    public int getItemCount() {
        return providerQualificationItem.size();
    }

    public ProviderRating getItemAtPosition(int position) {
        return providerQualificationItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<ProviderRating> data) {
        providerQualificationItem = new ArrayList<>();
        providerQualificationItem.addAll(data);
        notifyDataSetChanged();
    }

}




