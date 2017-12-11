package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Drinks;

/**
 * Created by Manu0 on 18/4/2017.
 */

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder> {

    private List<Drinks> drinksItem = new ArrayList<>();
    private List<Drinks> selectedDrinkList = new ArrayList<>();

    private Context mContext;

    public DrinksAdapter(List<Drinks> drinksList) {
        drinksItem = drinksList;
    }

    public class DrinksViewHolder extends RecyclerView.ViewHolder {
        TextView mDrinkTextView;
        TextView mProviderDescription;
        TextView mAmountDrink;
        AppCompatCheckBox mSelectedChechBox;


        public DrinksViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mDrinkTextView = (TextView) view.findViewById(R.id.item_drink_description);
            mProviderDescription = (TextView) view.findViewById(R.id.item_drink_provider);
            mAmountDrink = (TextView) view.findViewById(R.id.item_drink_amount);
            mSelectedChechBox = (AppCompatCheckBox) view.findViewById(R.id.drinks_check_box);

        }
    }

    @Override
    public DrinksAdapter.DrinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drinks, parent, false);
        return new DrinksAdapter.DrinksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DrinksAdapter.DrinksViewHolder holder, final int position) {
        holder.mDrinkTextView.setText("Bebida: " + drinksItem.get(position).getDescription());
        holder.mProviderDescription.setText("Proveedor: " + drinksItem.get(position).getProvider());
        holder.mAmountDrink.setText("Precio: " + drinksItem.get(position).getPriceUnit());
        holder.mSelectedChechBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Drinks drinksObject = new Drinks();
                    drinksObject = drinksItem.get(position);
                    selectedDrinkList.add(drinksObject);
                } else {
                    selectedDrinkList.remove(drinksItem.get(position));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return drinksItem.size();
    }

    @Override
    public long getItemId(int position) {
        return drinksItem.get(position).getId();
    }

    public Drinks getItemAtPosition(int position) {
        return drinksItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<Drinks> data) {
        drinksItem = new ArrayList<>();
        drinksItem.addAll(data);
        notifyDataSetChanged();
    }


    public List<Drinks> getSelectedDrinks() {
        return selectedDrinkList;
    }

}



