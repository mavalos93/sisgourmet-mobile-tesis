package tesis.com.py.sisgourmetmobile.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.AllCommentsAdapter;
import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.ProviderRating;
import tesis.com.py.sisgourmetmobile.repositories.AllCommentsRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class CommentsDetailActivity extends AppCompatActivity {

    //VIEW
    private TextView mQualificationValue;
    private TextView mTotalCommentsValue;
    private AppCompatRatingBar mRatingBar;
    private ProgressBar mOneProgressBar;
    private ProgressBar mTwoProgressBar;
    private ProgressBar mThreeProgressBar;
    private ProgressBar mFourProgressBar;
    private ProgressBar mFiveProgressBar;
    private TextView mOneStarCounterValue;
    private TextView mTwoStarCounterValue;
    private TextView mThreeStarCounterValue;
    private TextView mFourCounterValue;
    private TextView mFiveCounterValue;
    private RecyclerView mCommentsRecyclerView;
    private AllCommentsAdapter mAdapter;
    private Toolbar toolbar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQualificationValue = findViewById(R.id.total_rating_provider);
        mTotalCommentsValue = findViewById(R.id.total_comments_provider);
        mRatingBar = findViewById(R.id.provider_ratingbar);
        mOneStarCounterValue = findViewById(R.id.one_total_comments);
        mTwoStarCounterValue = findViewById(R.id.two_total_comments);
        mThreeStarCounterValue = findViewById(R.id.three_total_comments);
        mFourCounterValue = findViewById(R.id.four_total_comments);
        mFiveCounterValue = findViewById(R.id.five_total_comments);

        mOneProgressBar = findViewById(R.id.one_rating_start);
        mTwoProgressBar = findViewById(R.id.two_rating_start);
        mThreeProgressBar = findViewById(R.id.three_rating_start);
        mFourProgressBar = findViewById(R.id.four_rating_start);
        mFiveProgressBar = findViewById(R.id.five_rating_start);


        mCommentsRecyclerView = findViewById(R.id.comments_recyclerview);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AllCommentsAdapter(new ArrayList<Comments>(), this);
        mCommentsRecyclerView.setAdapter(mAdapter);
        mCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommentsRecyclerView.setHasFixedSize(true);
        mCommentsRecyclerView.setNestedScrollingEnabled(false);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getProviderObject();


    }

    private void getProviderObject() {

        try {
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            if (bundle != null) {
                ProviderRating providerRating = (ProviderRating) bundle.get(Constants.ACTION_COMMENTS);
                if (providerRating != null) {
                    setupData(providerRating);
                } else {
                    Utils.builToast(this, getString(R.string.error_get_provider_object));
                    finish();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void setupData(ProviderRating mProviderRating) {
        toolbar.setTitle(mProviderRating.getProviderName());
        mQualificationValue.setText(String.valueOf(Utils.setupRatingValue(mProviderRating.getProviderRating())).replace(".", ","));
        mTotalCommentsValue.setText(String.valueOf(mProviderRating.getTotalUserComments()));
        mRatingBar.setRating(Utils.setupRatingValue(mProviderRating.getProviderRating()));
        mOneStarCounterValue.setText(String.valueOf(mProviderRating.getOneStar()));
        mTwoStarCounterValue.setText(String.valueOf(mProviderRating.getTwoStar()));
        mThreeStarCounterValue.setText(String.valueOf(mProviderRating.getThreeStar()));
        mFourCounterValue.setText(String.valueOf(mProviderRating.getFourStar()));
        mFiveCounterValue.setText(String.valueOf(mProviderRating.getFiveStar()));

        mOneProgressBar.setMax(mProviderRating.getTotalUserComments());
        mOneProgressBar.setProgress(mProviderRating.getOneStar());

        mTwoProgressBar.setMax(mProviderRating.getTotalUserComments());
        mTwoProgressBar.setProgress(mProviderRating.getTwoStar());

        mThreeProgressBar.setMax(mProviderRating.getTotalUserComments());
        mThreeProgressBar.setProgress(mProviderRating.getThreeStar());

        mFourProgressBar.setMax(mProviderRating.getTotalUserComments());
        mFourProgressBar.setProgress(mProviderRating.getFourStar());

        mFiveProgressBar.setMax(mProviderRating.getTotalUserComments());
        mFiveProgressBar.setProgress(mProviderRating.getFiveStar());

        mAdapter.setData(AllCommentsRepository.getCommentsByProviderId(mProviderRating.getProviderId()));

    }

}
