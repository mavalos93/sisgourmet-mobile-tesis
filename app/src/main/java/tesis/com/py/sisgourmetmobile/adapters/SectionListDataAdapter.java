package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.MainStepper;
import tesis.com.py.sisgourmetmobile.activities.QualificationActivity;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;


/**
 * Created by Manu0 on 25/4/2017.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<Lunch> itemsList;
    private Context mContext;
    private Lunch mLunchObject = new Lunch();

    public SectionListDataAdapter(Context context, ArrayList<Lunch> itemsList) {
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
        Provider mProviderObject;

        List<Garnish> garnisList = GarnishRepository.getGarnishByLunchId(lunchItem.getPrincipalMenuCode());

        listSize = garnisList.size();
        if (listSize != 0) {
            if (listSize == 1) {
                for (Garnish gr : garnisList) {
                    mGarnishText = gr.getDescription();
                }
            } else if (listSize > 1) {
                mGarnishText = "Combinable";
                holder.garnishDescription.setTextColor(mContext.getResources().getColor(R.color.accent));
            }
        }

        mRatingValue = Float.parseFloat(String.valueOf(lunchItem.getRatingMenu()));

        mProviderObject = ProviderRepository.getProviderById(lunchItem.getProviderId());

        if (mProviderObject != null) {

            switch (mProviderObject.getProviderName()) {
                case "La Vienesa":
                    holder.itemImage.setImageResource(R.mipmap.login);
                    break;
                case "Ña Eustaquia":
                    holder.itemImage.setImageResource(R.mipmap.nha_esutaquia);
                    break;
                case "Bolsi":
                    holder.itemImage.setImageResource(R.mipmap.bolsi);
                    break;

            }
        }

        holder.mainMenuDescription.setText(lunchItem.getMainMenuDescription());
        holder.garnishDescription.setText(mGarnishText);
        holder.mQualificationMenuValue.setText(String.valueOf(mRatingValue).replace(".",","));

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

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        private TextView mainMenuDescription;
        private ImageView itemImage;
        private TextView garnishDescription;
        private TextView mQualificationMenuValue;
        private ImageButton mQualificationButton;


        public SingleItemRowHolder(View view) {
            super(view);

            this.mainMenuDescription = (TextView) view.findViewById(R.id.main_menu_desciption);
            this.garnishDescription = (TextView) view.findViewById(R.id.garnish_description);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.mQualificationMenuValue = (TextView) view.findViewById(R.id.qualification_menu_value);
            this.mQualificationButton = (ImageButton) view.findViewById(R.id.star_imageButton);


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
