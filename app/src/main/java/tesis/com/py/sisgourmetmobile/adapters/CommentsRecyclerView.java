package tesis.com.py.sisgourmetmobile.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatButton;
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


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tesis.com.py.sisgourmetmobile.R;

import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.recivers.MyCommentsObserver;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.services.SendCommentService;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 31/5/2017.
 */

public class CommentsRecyclerView extends RecyclerView.Adapter<CommentsRecyclerView.CommentViewHolder> {

    private List<Qualification> qualificationItem = new ArrayList<>();
    private Context mContext;
    private Dialog dialog;


    public CommentsRecyclerView(List<Qualification> qualificationList, Context context) {
        qualificationItem = qualificationList;
        mContext = context;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView mCommentTextView;
        TextView mMyLunchTextView;
        TextView mProviderDescription;
        CircleImageView mUserImageView;
        TextView mQualificationValue;
        AppCompatRatingBar mRatingBar;
        TextView mDateQualificationTextView;
        ImageButton mMoreDetailsButton;
        ImageButton mFavoriteButton;
        ImageView mStatusIcon;


        CommentViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mCommentTextView = view.findViewById(R.id.item_comment_description);
            mMyLunchTextView = view.findViewById(R.id.item_comment_user_selected_menu);
            mUserImageView = view.findViewById(R.id.image_view_first_name);
            mRatingBar = view.findViewById(R.id.qualification_rating_bar);
            mProviderDescription = view.findViewById(R.id.provider_description);
            mQualificationValue = view.findViewById(R.id.qualification_value);
            mMoreDetailsButton = view.findViewById(R.id.more_details_button);
            mDateQualificationTextView = view.findViewById(R.id.date_qualfication_textView);
            mFavoriteButton = view.findViewById(R.id.favorite_button);
            mStatusIcon = view.findViewById(R.id.item_status_comment);

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
    public CommentsRecyclerView.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentsRecyclerView.CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentsRecyclerView.CommentViewHolder holder, final int position) {
        Qualification mQualificationObject = qualificationItem.get(position);

        if (mQualificationObject.getGarnish().equals("")) {
            holder.mMyLunchTextView.setText(mQualificationObject.getMainMenu().toLowerCase());
        } else {
            holder.mMyLunchTextView.setText(mQualificationObject.getMainMenu().toLowerCase() + " " + "y" + " " + mQualificationObject.getGarnish());

        }
        setupUserImage(holder);
        Provider provider = ProviderRepository.getProviderById(mQualificationObject.getProviderId());
        holder.mProviderDescription.setText((provider == null ? "Sin Proveedor" : provider.getProviderName().toUpperCase()));
        holder.mQualificationValue.setText(String.valueOf(mQualificationObject.getQualificationValue()) + ",0");

        holder.mDateQualificationTextView.setText(mQualificationObject.getSendAppAt());
        holder.mCommentTextView.setText(mQualificationObject.getCommentary().toLowerCase());

        //get first letter of each String item

        if (mQualificationObject.getOrder() != 0) {
            holder.mFavoriteButton.setBackgroundResource(R.mipmap.ic_star_border_yellow_36dp);
        } else {
            holder.mFavoriteButton.setBackgroundResource(R.mipmap.ic_star_border_36_dp);

        }
        holder.mRatingBar.setRating(Utils.setupRatingValue(String.valueOf(mQualificationObject.getQualificationValue())));

        holder.mMoreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(mContext, holder, qualificationItem.get(position));
            }
        });

        switch (mQualificationObject.getStatusSend()) {
            case Constants.TRANSACTION_SEND:
                holder.mStatusIcon.setImageResource(R.mipmap.ic_success_flaticons);
                break;
            case Constants.TRANSACTION_NO_SEND:
                holder.mStatusIcon.setImageResource(R.mipmap.ic_pending_flaticons);
                break;
        }
    }

    private void setupUserImage(CommentsRecyclerView.CommentViewHolder holder) {
        User mUserObject = UserRepository.getUser(mContext);
        if (mUserObject != null) {
            if (mUserObject.getImageProfile() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(mUserObject.getImageProfile(), 0, mUserObject.getImageProfile().length);
                holder.mUserImageView.setImageBitmap(bmp);
            }
        }
    }

    private void showPopup(Context context, final CommentsRecyclerView.CommentViewHolder holder, final Qualification qualification) {
        //creating a popup menu
        PopupMenu popup = new PopupMenu(context, holder.mMoreDetailsButton);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_item_my_favorite);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.id_action_send:
                        if (Utils.checkNetworkConnection(mContext)) {
                            Utils.builToast(mContext,mContext.getString(R.string.sending_comment));
                            sendTransaction(qualification, holder);
                        } else {
                            Utils.builToast(mContext, mContext.getString(R.string.tag_not_internet));
                        }
                        break;
                    case R.id.id_action_view_details:
                        showCommentDialog(qualification);
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }


    private void sendTransaction(Qualification qualification, CommentsRecyclerView.CommentViewHolder holder) {
        switch (qualification.getStatusSend()) {
            case Constants.TRANSACTION_SEND:
                Utils.builToast(mContext, mContext.getString(R.string.order_alredy_send));
                break;
            case Constants.TRANSACTION_NO_SEND:
                SendCommentService.startSendTransaction(mContext, qualification.getId());
                break;
        }
    }

    private void showCommentDialog(Qualification mQualificationObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogView = layoutInflater.inflate(R.layout.comment_details_dialog, null);
        TextView mUserNameTextView = dialogView.findViewById(R.id.user_name_textView);
        AppCompatRatingBar mRatingBar = dialogView.findViewById(R.id.user_comment_ratingbar);
        TextView mDateTextView = dialogView.findViewById(R.id.date_user_commnet);
        AppCompatButton mAcceptButton = dialogView.findViewById(R.id.dismis_button);
        TextView mCommentTextView = dialogView.findViewById(R.id.all_commentview);
        ImageView mUserImage = dialogView.findViewById(R.id.user_image_comment);
        TextView mSelectedMenu = dialogView.findViewById(R.id.main_menu_textView);
        setupUserImage(mUserImage);
        mSelectedMenu.setText(mQualificationObject.getMainMenu());
        mUserNameTextView.setText(mQualificationObject.getUser().toUpperCase());
        mRatingBar.setRating(Utils.setupRatingValue(String.valueOf(mQualificationObject.getQualificationValue())));
        mDateTextView.setText(mQualificationObject.getSendAppAt());
        mCommentTextView.setText(mQualificationObject.getCommentary());
        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setupUserImage(ImageView mUserImage) {
        User mUserObject = UserRepository.getUser(mContext);
        if (mUserObject != null) {
            if (mUserObject.getImageProfile() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(mUserObject.getImageProfile(), 0, mUserObject.getImageProfile().length);
                mUserImage.setImageBitmap(bmp);
            }
        }
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


}



