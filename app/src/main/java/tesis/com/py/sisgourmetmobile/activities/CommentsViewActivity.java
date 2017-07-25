package tesis.com.py.sisgourmetmobile.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.AllCommentsAdapter;
import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.AllCommentsRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.utils.DividerItemDecoration;
import tesis.com.py.sisgourmetmobile.utils.RecyclerItemClickListener;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class CommentsViewActivity extends AppCompatActivity {

    //View
    private TextView mRatingNoteVienesa;
    private AppCompatRatingBar mVienesaRatingBar;
    private AppCompatTextView mTotalUserCommentVienesa;


    private TextView mRatingNoteNhaEustaquia;
    private AppCompatRatingBar mNhaEustaquiaRatingBar;
    private AppCompatTextView mTotalUserCommentNhaEustaquia;

    private TextView mRatingNotBolsi;
    private AppCompatRatingBar mBolsiRatingBar;
    private AppCompatTextView mTotalUserCommentBolsi;


    private RecyclerView mRecyclerView;
    private ProgressBar mVienesaProgressBar;
    private ProgressBar mNhaEustaquiaProgressBar;
    private ProgressBar mBolsiProgressBar;

    // Adapter
    private AllCommentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commets_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mVienesaProgressBar = (ProgressBar) findViewById(R.id.rating_note_la_vienesa_progressBar);
        mRatingNoteVienesa = (TextView) findViewById(R.id.rating_note_la_vienesa_textView);
        mVienesaRatingBar = (AppCompatRatingBar) findViewById(R.id.comments_vienesa_rating_bar);
        mTotalUserCommentVienesa = (AppCompatTextView) findViewById(R.id.total_la_vienesa_comments_textView);

        mNhaEustaquiaProgressBar = (ProgressBar) findViewById(R.id.rating_note_nha_eustaquea_progressBar);
        mRatingNoteNhaEustaquia = (TextView) findViewById(R.id.rating_note_la_nha_eustaquea_textView);
        mNhaEustaquiaRatingBar = (AppCompatRatingBar) findViewById(R.id.comments_nha_esutaquia_rating_bar);
        mTotalUserCommentNhaEustaquia = (AppCompatTextView) findViewById(R.id.total_nha_eustaquia_comments);

        mBolsiProgressBar = (ProgressBar) findViewById(R.id.rating_note_bolsi_progressBar);
        mRatingNotBolsi = (TextView) findViewById(R.id.rating_note_la_bolsi_textView);
        mBolsiRatingBar = (AppCompatRatingBar) findViewById(R.id.commens_bolsi_rating_bar);
        mTotalUserCommentBolsi = (AppCompatTextView) findViewById(R.id.total_bolsi_comments);


        mRecyclerView = (RecyclerView) findViewById(R.id.generalCommentsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);


        mAdapter = new AllCommentsAdapter(new ArrayList<Comments>(), this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        setupProviderData();
    }


    private void setupProviderData() {
        // Charge all comment data
        mAdapter.setData(AllCommentsRepository.getAll());
        // setup view this provider (Vienesa)
        Utils.ProgressBarAnimation animVienesa = new Utils.ProgressBarAnimation(mVienesaProgressBar, 0, 51);
        animVienesa.setDuration(1000);
        mVienesaProgressBar.startAnimation(animVienesa);


        Utils.ProgressBarAnimation animBolsi = new Utils.ProgressBarAnimation(mBolsiProgressBar, 0, 30);
        animBolsi.setDuration(1000);
        mBolsiProgressBar.startAnimation(animBolsi);
        Utils.ProgressBarAnimation animNhaEustaquia = new Utils.ProgressBarAnimation(mNhaEustaquiaProgressBar, 0, 86.7);
        animNhaEustaquia.setDuration(1000);
        mNhaEustaquiaProgressBar.startAnimation(animNhaEustaquia);

    }


}
