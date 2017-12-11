package tesis.com.py.sisgourmetmobile.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.SummaryDrinksAdapter;
import tesis.com.py.sisgourmetmobile.adapters.SummaryOrderAdapter;
import tesis.com.py.sisgourmetmobile.entities.SummaryOrder;
import tesis.com.py.sisgourmetmobile.repositories.SummaryOrderRepository;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class SummaryOrderActivity extends AppCompatActivity {

    // VIEW
    private RecyclerView mRecyclerView;
    private SummaryOrderAdapter mAdapter;
    private TextView mYearValue;
    private TextView mMonthValue;
    private TextView mTotalAmountValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mYearValue = findViewById(R.id.year);
        mMonthValue = findViewById(R.id.month);
        mTotalAmountValue = findViewById(R.id.total_using_amount);


        mRecyclerView = findViewById(R.id.summary_order_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SummaryOrderAdapter(new ArrayList<SummaryOrder>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        setData();

    }

    private void setData() {
        mYearValue.setText(String.valueOf(Utils.getYear(new Date())));
        mMonthValue.setText(Utils.mapMonthName(new Date()));
        mAdapter.setData(SummaryOrderRepository.getDataByYearAndMonth(Utils.getMonth(new Date()), Utils.getYear(new Date())));
        mTotalAmountValue.setText(String.valueOf(mAdapter.getTotalSpendingOfMonth()));
    }

}
