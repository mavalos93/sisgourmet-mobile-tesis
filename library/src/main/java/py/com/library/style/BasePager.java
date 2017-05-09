package py.com.library.style;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


import java.util.List;

import py.com.library.AbstractStep;
import py.com.library.R;
import py.com.library.adapter.PageAdapter;
import py.com.library.adapter.PageChangeAdapter;
import py.com.library.adapter.PageStateAdapter;
import py.com.library.interfaces.Pageable;

/**
 * @author Francesco Cannizzaro (fcannizzaro).
 */
public class BasePager extends BaseStyle {

    // view
    protected ViewPager mPager;

    // adapters
    protected Pageable mPagerAdapter;

    protected void init() {
        super.init();
        mPager = (ViewPager) findViewById(R.id.stepPager);
        assert mPager != null;
        mPager.setAdapter((PagerAdapter) mPagerAdapter);
        mSteps.get(0).onStepVisible();
        mPager.addOnPageChangeListener(new PageChangeAdapter() {
            @Override
            public void onPageSelected(int position) {
                mSteps.get(position).onStepVisible();
            }
        });
    }

    private void initAdapter() {
        if (mPagerAdapter == null)
            mPagerAdapter = (Pageable) (getStateAdapter() ? new PageStateAdapter(getSupportFragmentManager()) : new PageAdapter(getSupportFragmentManager()));
    }

    @Override
    public void addStep(AbstractStep step) {
        super.addStep(step);
        initAdapter();
        mPagerAdapter.add(step);
    }

    @Override
    public void addSteps(List<AbstractStep> steps) {
        super.addSteps(steps);
        initAdapter();
        mPagerAdapter.set(mSteps.getSteps());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        mPager.setCurrentItem(mSteps.current());
    }

}
