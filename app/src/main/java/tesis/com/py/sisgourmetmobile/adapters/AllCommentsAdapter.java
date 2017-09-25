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
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Comments;


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

    public class AllCommetViewHolder extends RecyclerView.ViewHolder {

        TextView mUserTextView;
        TextView mCommentTextView;
        TextView mMyLunchTextView;
        ImageView mFirstNameImageView;
        AppCompatRatingBar mRatingBar;
        TextView mDateQualificationTextView;


        public AllCommetViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mUserTextView = (TextView) view.findViewById(R.id.item_all_comment_user_name);
            mCommentTextView = (TextView) view.findViewById(R.id.item_all_comment_description);
            mMyLunchTextView = (TextView) view.findViewById(R.id.item_all_comment_user_selected_menu);
            mFirstNameImageView = (ImageView) view.findViewById(R.id.image_view_first_name);
            mRatingBar = (AppCompatRatingBar) view.findViewById(R.id.qualification_rating_bar);
            mDateQualificationTextView = (TextView) view.findViewById(R.id.date_qualfication_textView);

        }
    }

    @Override
    public AllCommentsAdapter.AllCommetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_comments, parent, false);
        return new AllCommentsAdapter.AllCommetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AllCommentsAdapter.AllCommetViewHolder holder, final int position) {
        Comments mCommentObject = commentsItem.get(position);
        String firstLetter = "";

        if (mCommentObject.getUserName() != null || !mCommentObject.getUserName().equals("null") || !mCommentObject.getUserName().equals("")) {
            firstLetter = String.valueOf(mCommentObject.getUserName().charAt(0)).toUpperCase();
        }


        holder.mUserTextView.setText(mCommentObject.getUserName());
        holder.mCommentTextView.setText(mCommentObject.getCommentDescription());
        holder.mMyLunchTextView.setText(mCommentObject.getLunchPackageDescription());
        holder.mDateQualificationTextView.setText(mCommentObject.getDateComment());
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color = generator.getColor(commentsItem.get(position));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px
        holder.mFirstNameImageView.setImageDrawable(drawable);

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




