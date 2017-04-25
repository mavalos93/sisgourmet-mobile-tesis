package tesis.com.py.sisgourmetmobile.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.ProviderSelectedActivity;
import tesis.com.py.sisgourmetmobile.entities.Order;

/**
 * Created by Manu0 on 23/1/2017.
 */

public class MenuFragment extends Fragment {

    private OnItemMenuListener mListener;
    private View rootView;
    private AppCompatButton mMyLaunchButton;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        mMyLaunchButton = (AppCompatButton) rootView.findViewById(R.id.id_launch_button);

        mMyLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProviderSelectedActivity.class));
            }
        });
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemMenuListener) {
            mListener = (OnItemMenuListener) context;
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


    public interface OnItemMenuListener {
        // TODO: Update argument type and name
        void onItemMenuSelectedListener(Menu menu);
    }





}


