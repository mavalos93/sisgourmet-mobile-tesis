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

import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.MainStepper;
import tesis.com.py.sisgourmetmobile.activities.QualificationActivity;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;


/**
 * Created by Manu0 on 25/4/2017.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private List<Lunch> itemsList;
    private Context mContext;
    private Lunch mLunchObject = new Lunch();

    public SectionListDataAdapter(Context context, List<Lunch> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {
        final Lunch lunchItem = itemsList.get(i);
        String mGarnishText = "";
        int listSize = 0;
        float mRatingValue;

        List<Garnish> garnisList = GarnishRepository.getGarnishByLunchId(lunchItem.getPrincipalMenuCode());

        listSize = garnisList.size();
        if (listSize != 0) {
            if (listSize == 1) {
                for (Garnish gr : garnisList) {
                    mGarnishText = gr.getDescription();
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
        return (null != itemsList ? itemsList.size() : 0);
    }

    class SingleItemRowHolder extends RecyclerView.ViewHolder {

        private TextView mainMenuDescription;
        private ImageView itemImage;
        private TextView garnishDescription;
        private TextView mQualificationMenuValue;
        private ImageButton mQualificationButton;


        SingleItemRowHolder(View view) {
            super(view);

            this.mainMenuDescription = view.findViewById(R.id.main_menu_desciption);
            this.garnishDescription = view.findViewById(R.id.garnish_description);
            this.itemImage = view.findViewById(R.id.itemImage);
            this.mQualificationMenuValue = view.findViewById(R.id.qualification_menu_value);
            this.mQualificationButton = view.findViewById(R.id.star_imageButton);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLunchObject = itemsList.get(getAdapterPosition());
                    Bundle menuBundle = new Bundle();
                    menuBundle.putSerializable(Constants.ACTION_SELECTED_MENU, mLunchObject);
                    Intent menuIntent = new Intent(mContext, MainStepper.class);
                    menuIntent.putExtra(Constants.SERIALIZABLE, menuBundle);
                    mContext.startActivity(menuIntent);
                }
            });
        }
    }
}
