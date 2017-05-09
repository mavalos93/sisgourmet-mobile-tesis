package py.com.library.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

import py.com.library.AbstractStep;
import py.com.library.interfaces.Pageable;

/**
 * @author Francesco Cannizzaro (fcannizzaro).
 */

public class PageStateAdapter extends FragmentStatePagerAdapter implements Pageable {

    private ArrayList<AbstractStep> fragments = new ArrayList<>();

    public PageStateAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void add(AbstractStep fragment) {
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public void set(List<AbstractStep> fragments) {
        this.fragments.clear();
        this.fragments.addAll(fragments);
        notifyDataSetChanged();
    }

    @Override
    public AbstractStep getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
