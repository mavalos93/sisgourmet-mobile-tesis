package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;

import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.recivers.MyCommentsObserver;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;

/**
 * Created by Manu0 on 31/5/2017.
 */

public class CommetsRecyclerView extends RecyclerView.Adapter<CommetsRecyclerView.CommetViewHolder> {

    private List<Qualification> qualificationItem = new ArrayList<>();
    private Context mContext;


    public CommetsRecyclerView(List<Qualification> qualificationList, Context context) {
        qualificationItem = qualificationList;
        mContext = context;
    }

    class CommetViewHolder extends RecyclerView.ViewHolder {

        TextView mUserTextView;
        TextView mCommentTextView;
        TextView mMyLunchTextView;
        ImageView mFirstNameImageView;
        TextView mQualificationValue;
        AppCompatRatingBar mRatingBar;
        TextView mDateQualificationTextView;
        ImageButton mFavoriteButton;
        ImageButton mMoreDetailsButton;


        CommetViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mUserTextView = view.findViewById(R.id.item_comment_user_name);
            mCommentTextView = view.findViewById(R.id.item_comment_description);
            mMyLunchTextView = view.findViewById(R.id.item_comment_user_selected_menu);
            mFirstNameImageView = view.findViewById(R.id.image_view_first_name);
            mRatingBar = view.findViewById(R.id.qualification_rating_bar);
            mQualificationValue = view.findViewById(R.id.qualification_value);
            mDateQualificationTextView = view.findViewById(R.id.date_qualfication_textView);
            mMoreDetailsButton = view.findViewById(R.id.more_details_button);
            mFavoriteButton = view.findViewById(R.id.favorite_button);

            mFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Qualification qualification = getItemAtPosition(getAdapterPosition());
                    if (qualification.getOrder() != 0) {
                        qualification.setOrder(0);
                    } else {
                        qualification.setOrder(QualificationRepository.getQualificationDesc().get(0).getOrder() + 1);
                    }
                    QualificationRepository.getDao().insertOrReplace(qualification);
                    mContext.sendBroadcast(new Intent(MyCommentsObserver.ACTION_LOAD_MY_COMMENTS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    notifyDataSetChanged();
                }
            });



        }
    }

    @Override
    public CommetsRecyclerView.CommetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommetsRecyclerView.CommetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommetsRecyclerView.CommetViewHolder holder, final int position) {
        Qualification mQualificationObject = qualificationItem.get(position);
        String firstLetter = "";

        holder.mUserTextView.setText(mQualificationObject.getUser());
        if (mQualificationObject.getGarnish().equals("")) {
            holder.mMyLunchTextView.setText(mQualificationObject.getMainMenu().toLowerCase());
        } else {
            holder.mMyLunchTextView.setText(mQualificationObject.getMainMenu().toLowerCase() + " " + "y" + " " + mQualificationObject.getGarnish());

        }
        firstLetter = String.valueOf(mQualificationObject.getUser().charAt(0)).toUpperCase();
        holder.mQualificationValue.setText(String.valueOf(mQualificationObject.getQualificationValue())+",0");

        holder.mDateQualificationTextView.setText(mQualificationObject.getSendAppAt());
        holder.mCommentTextView.setText(mQualificationObject.getCommentary().toLowerCase());

        //get first letter of each String item

        if (mQualificationObject.getOrder() != 0) {
            holder.mFavoriteButton.setBackgroundResource(R.mipmap.ic_star_border_yellow_36dp);
        } else {
            holder.mFavoriteButton.setBackgroundResource(R.mipmap.ic_star_border_36_dp);

        }

        holder.mMoreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(mContext,holder);
            }
        });

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(qualificationItem.get(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px
        holder.mFirstNameImageView.setImageDrawable(drawable);
        setupRatingValue(mQualificationObject, holder);

    }

    private void showPopup(Context context,CommetsRecyclerView.CommetViewHolder holder){
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.mMoreDetailsButton);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_item_my_favorite);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_action_view_details:
                        //handle menu1 click
                        break;
                    case R.id.id_action_send:
                        //handle menu2 click
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

    @Override
    public int getItemCount() {
        return qualificationItem.size();
    }

    @Override
    public long getItemId(int position) {
        return qualificationItem.get(position).getId();
    }

    public Qualification getItemAtPosition(int position) {
        return qualificationItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<Qualification> data) {
        qualificationItem = new ArrayList<>();
        qualificationItem.addAll(data);
        notifyDataSetChanged();
    }

    private void setupRatingValue(Qualification qualification, CommetsRecyclerView.CommetViewHolder holder) {
        String stringRaiting = String.valueOf(qualification.getQualificationValue());
        float mRaitingValue = Float.parseFloat(stringRaiting);

        switch (stringRaiting) {
            case "1":
                holder.mRatingBar.setRating(mRaitingValue);
                break;
            case "2":
                holder.mRatingBar.setRating(mRaitingValue);
                break;
            case "3":
                holder.mRatingBar.setRating(mRaitingValue);
                break;
            case "4":
                holder.mRatingBar.setRating(mRaitingValue);
                break;
            case "5":
                holder.mRatingBar.setRating(mRaitingValue);
                break;
        }
    }

}



