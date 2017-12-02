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
import android.view.View;
import android.widget.TextView;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Comments;

public class CommentsDetailActivity extends AppCompatActivity {

    //VIEW
    private TextView mQualificationValue;
    private TextView mTotalCommentsValue;
    private AppCompatRatingBar mRatingBar;
    private TextView mOneStarCounterValue;
    private TextView mTwoStarCounterValue;
    private TextView mThreeStarCounterValue;
    private TextView mFourCounterValue;
    private TextView mFiveCounterValue;
    private RecyclerView mCommentsRecyclerView;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQualificationValue = findViewById(R.id.total_rating_provider);
        mTotalCommentsValue = findViewById(R.id.total_comments_provider);
        mRatingBar = findViewById(R.id.provider_ratingbar);
        mOneStarCounterValue = findViewById(R.id.one_total_comments);
        mTwoStarCounterValue = findViewById(R.id.two_total_comments);
        mThreeStarCounterValue = findViewById(R.id.three_total_comments);
        mFourCounterValue = findViewById(R.id.four_total_comments);
        mFiveCounterValue = findViewById(R.id.five_total_comments);

        mCommentsRecyclerView = findViewById(R.id.comments_recyclerview);
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mAdapter = new CommentsRecyclerView(new ArrayList<Comments>(), this);
       // mCommentsRecyclerView.setAdapter(mAdapter);
        mCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

}
