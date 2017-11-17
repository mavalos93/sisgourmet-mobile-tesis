package tesis.com.py.sisgourmetmobile.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.models.SectionDataModel;

/**
 * Created by Manu0 on 25/4/2017.
 */

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private List<SectionDataModel> itemDataList = new ArrayList<>();
    private Context mContext;

    public RecyclerViewDataAdapter(Context context, List<SectionDataModel> dataList) {
        this.itemDataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = itemDataList.get(i).getHeaderTitle();

        List singleSectionItems = itemDataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);
        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

    }

    public void setData(List<SectionDataModel> data) {
        itemDataList = new ArrayList<>();
        itemDataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != itemDataList ? itemDataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);


        }

    }
}
