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
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.models.ProviderQualificationModel;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 9/10/2017.
 */

public class ProviderQualificationAdapter extends RecyclerView.Adapter<ProviderQualificationAdapter.ProviderQualificationViewHolder> {

    private List<ProviderQualificationModel> providerQualificationItem = new ArrayList<>();
    private Context mContext;


    public ProviderQualificationAdapter(List<ProviderQualificationModel> providerQualificationModelList, Context context) {
        providerQualificationItem = providerQualificationModelList;
        mContext = context;
    }

    class ProviderQualificationViewHolder extends RecyclerView.ViewHolder {

        TextView mProviderRatingValue;
        RatingBar mProviderRatingBar;
        TextView mProviderName;
        TextView mTotalUsersComments;
        ProgressBar mProviderProgressBar;
        ImageView mProviderImageView;


        ProviderQualificationViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mProviderRatingValue = (TextView) view.findViewById(R.id.provider_rating_value);
            mProviderRatingBar = (RatingBar) view.findViewById(R.id.provider_ratingBar);
            mProviderName = (TextView) view.findViewById(R.id.provider_name);
            mTotalUsersComments = (TextView) view.findViewById(R.id.total_user_comments_textView);
            mProviderProgressBar = (ProgressBar) view.findViewById(R.id.provider_progressBar);
            mProviderImageView = (ImageView) view.findViewById(R.id.provider_imageView);


        }
    }

    @Override
    public ProviderQualificationAdapter.ProviderQualificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_provider_value, parent, false);
        return new ProviderQualificationAdapter.ProviderQualificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProviderQualificationAdapter.ProviderQualificationViewHolder holder, final int position) {
        ProviderQualificationModel pqm = getItemAtPosition(position);

        try {
            holder.mProviderName.setText(pqm.getProviderName());
            holder.mProviderRatingValue.setText(pqm.getProviderRating());
            Utils.ProgressBarAnimation animVienesa = new Utils.ProgressBarAnimation(holder.mProviderProgressBar, 0, pqm.getProviderMaxValue());
            animVienesa.setDuration(1000);
            holder.mProviderProgressBar.startAnimation(animVienesa);
            Bitmap bmp = BitmapFactory.decodeByteArray(pqm.getFileArrayImage(), 0, pqm.getFileArrayImage().length);
            holder.mProviderImageView.setImageBitmap(bmp);
            holder.mProviderRatingBar.setRating(Float.parseFloat(pqm.getProviderRating()));
        } catch (Exception ex) {
            Utils.builToast(mContext,mContext.getString(R.string.error_in_buil_adapter_view));
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return providerQualificationItem.size();
    }

    public ProviderQualificationModel getItemAtPosition(int position) {
        return providerQualificationItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<ProviderQualificationModel> data) {
        providerQualificationItem = new ArrayList<>();
        providerQualificationItem.addAll(data);
        notifyDataSetChanged();
    }

}




