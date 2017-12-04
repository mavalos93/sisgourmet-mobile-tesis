package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.utils.Utils;


/**
 * Created by Manu0 on 19/7/2017.
 */

public class AllCommentsAdapter extends RecyclerView.Adapter<AllCommentsAdapter.AllCommetViewHolder> {

    private List<Comments> commentsItem = new ArrayList<>();
    private Context mContext;


    public AllCommentsAdapter(List<Comments> commentsList, Context context) {
        commentsItem = commentsList;
        mContext = context;
    }

    class AllCommetViewHolder extends RecyclerView.ViewHolder {

        private ImageView mUserImage;
        private TextView mUserName;
        private TextView mDate;
        private RatingBar mCommentRating;
        private TextView mCommentUser;
        private TextView mLunchDescription;

        AllCommetViewHolder(View view) {
            super(view);
            mContext = view.getContext();

            mUserImage = view.findViewById(R.id.user_comment_image);
            mUserName = view.findViewById(R.id.username_value);
            mDate = view.findViewById(R.id.date_comment);
            mCommentRating = view.findViewById(R.id.user_comment_ratingbar);
            mCommentUser = view.findViewById(R.id.data_user_comment);
            mLunchDescription = view.findViewById(R.id.lunch_package_description);


        }
    }

    @Override
    public AllCommentsAdapter.AllCommetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments_user, parent, false);
        return new AllCommentsAdapter.AllCommetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllCommentsAdapter.AllCommetViewHolder holder, final int position) {
        Comments mCommentObject = commentsItem.get(position);
        holder.mLunchDescription.setText(mCommentObject.getLunchPackageDescription());
        holder.mUserName.setText(mCommentObject.getUserName());
        holder.mDate.setText(mCommentObject.getDateComment());
        holder.mCommentRating.setRating(Utils.setupRatingValue(String.valueOf(mCommentObject.getRatingValue())));
        holder.mCommentUser.setText(mCommentObject.getCommentDescription());


    }

    @Override
    public int getItemCount() {
        return commentsItem.size();
    }

    @Override
    public long getItemId(int position) {
        return commentsItem.get(position).getId();
    }

    public Comments getItemAtPosition(int position) {
        return commentsItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<Comments> data) {
        commentsItem = new ArrayList<>();
        commentsItem.addAll(data);
        notifyDataSetChanged();
    }

}




