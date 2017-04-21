package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Provider;

/**
 * Created by Manu0 on 4/2/2017.
 */

public class ProviderRecyclerViewAdapter extends RecyclerView.Adapter<ProviderRecyclerViewAdapter.ProviderViewHolder> {

    private List<Provider> providerItem = new ArrayList<>();
    private Context mContext;

    public ProviderRecyclerViewAdapter(List<Provider> providerList) {
        providerItem = providerList;
    }

    public class ProviderViewHolder extends RecyclerView.ViewHolder {
        TextView mProviderTextView;
        TextView mProviderDescription;


        public ProviderViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mProviderTextView = (TextView) view.findViewById(R.id.item_provider_name);
            mProviderDescription = (TextView) view.findViewById(R.id.item_provider_description);

        }
    }

    @Override
    public ProviderRecyclerViewAdapter.ProviderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ProviderRecyclerViewAdapter.ProviderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProviderViewHolder holder, int position) {
        holder.mProviderTextView.setText(providerItem.get(position).getProviderName());
        holder.mProviderDescription.setText(providerItem.get(position).getProviderType());
    }

    @Override
    public int getItemCount() {
        return providerItem.size();
    }

    @Override
    public long getItemId(int position) {
        return providerItem.get(position).getId();
    }

    public Provider getItemAtPosition(int position) {
        return providerItem.get(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(List<Provider> data) {
        providerItem = new ArrayList<>();
        providerItem.addAll(data);
        notifyDataSetChanged();
    }


}


