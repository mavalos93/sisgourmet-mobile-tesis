package tesis.com.py.sisgourmetmobile.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tesis.com.py.sisgourmetmobile.R;

/**
 * Created by Manu0 on 11/22/2017.
 */

public class CommentsFragment extends Fragment {

    // VIEW
    private View rootView;

    public static CommentsFragment newInstance() {
        CommentsFragment fragment = new CommentsFragment();
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
        rootView = inflater.inflate(R.layout.comments_fragment, container, false);
        return rootView;
    }
}
