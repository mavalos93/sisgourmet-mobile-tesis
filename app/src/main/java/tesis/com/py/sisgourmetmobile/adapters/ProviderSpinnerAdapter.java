package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Provider;

/**
 * Created by Manu0 on 10/2/2017.
 */

public class ProviderSpinnerAdapter extends ArrayAdapter<Provider> {

    private List<Provider> mProviderList = new ArrayList<>();
    private int layoutResource;

    public ProviderSpinnerAdapter(Context context, int resource, List<Provider> data){
        super(context,resource,data);
        mProviderList = data;
        layoutResource = resource;
    }
    @Nullable
    @Override
    public Provider getItem(int position) {
        return mProviderList.get(position);
    }

    @Override
    public int getPosition(Provider item) {
        return mProviderList.indexOf(item);
    }

    @Override
    public int getCount() {
        return mProviderList.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position) {
        return mProviderList.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
            // Configure the view holder
            viewHolder.mProviderDescription = (TextView) convertView.findViewById(R.id.item_provider_spinner);
            convertView.setTag(viewHolder);
        } else {
            // Fill data from the recycled view holder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Provider provider = mProviderList.get(position);
        viewHolder.mProviderDescription.setText(provider.getProviderName());
        return convertView;
    }


    private static class ViewHolder {
        TextView mProviderDescription;
    }

}
