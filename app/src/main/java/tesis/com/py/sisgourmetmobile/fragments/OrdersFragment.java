package tesis.com.py.sisgourmetmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.OrderAdapter;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.loaders.OrderLoader;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;

/**
 * Created by mavalos on 23/02/17.
 */

public class OrdersFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Order>> {

    private OnItemOrderListenerSelected mListener;
    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View rootView;


    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TransactionFragment.
     */

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_orders);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new OrderAdapter(new ArrayList<Order>(), getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onItemOrderListenerSelected(mAdapter.getItemAtPosition(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemOrderListenerSelected) {
            mListener = (OnItemOrderListenerSelected) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemTransactioListenerSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<List<Order>> onCreateLoader(int id, Bundle args) {
        return new OrderLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Order>> loader, List<Order> data) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Order>> loader) {
        mAdapter.setData(new ArrayList<Order>());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnItemOrderListenerSelected {
        // TODO: Update argument type and name
        void onItemOrderListenerSelected(Order orders);
    }
}
