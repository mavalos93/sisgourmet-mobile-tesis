package tesis.com.py.sisgourmetmobile.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import py.com.library.style.TabStepper;
import py.com.library.util.LinearityChecker;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.dialogs.CancelableAlertDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.fragments.MenuFragment;
import tesis.com.py.sisgourmetmobile.fragments.OrdersFragment;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.DataSyncTest;
import tesis.com.py.sisgourmetmobile.utils.OperationList;
import tesis.com.py.sisgourmetmobile.utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuFragment.OnItemMenuListener,
        OrdersFragment.OnItemOrderListenerSelected,
        CancelableAlertDialogFragment.CancelableAlertDialogFragmentListener {
    private CoordinatorLayout mCoordinatorLayoutView;
    private ViewPager mViewPager;
    private final String TAG_CLASS = MainActivity.class.getName();

    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mCoordinatorLayoutView = (CoordinatorLayout) findViewById(R.id.main_coordinator_view);
        setupViewPager(mViewPager);
        setupTabIcons();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        localSync();
    }

    @Override
    public void onResume() {
        super.onResume();
        TabStepper.isDone = false;
        LinearityChecker.mDone.clear();
        StepLunch.radioGarnishId = 0;
        StepLunch.typeLunchCase = 0;
        StepDrinks.mSelectedDrinkItem.clear();

    }

    private void setupTabIcons() {
        try {
            TabLayout.Tab tab1 = mTabLayout.getTabAt(0);
            tab1.setIcon(R.drawable.menu_tab_selector);
            TabLayout.Tab tab2 = mTabLayout.getTabAt(1);
            tab2.setIcon(R.drawable.orders_tab_selector);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sesion) {
            logoutMethod();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(MenuFragment.newInstance(), getString(R.string.tab_menu));
        adapter.addFrag(OrdersFragment.newInstance(), getString(R.string.tab_orders));
        viewPager.setAdapter(adapter);
    }

    private void localSync() {
        DataSyncTest.setProviderData();
        DataSyncTest.setMenuData();
        DataSyncTest.setDrinks();
        DataSyncTest.setGarnish();
    }

    private void logoutMethod() {

        CancelableAlertDialogFragment cancelableAlertDialogFragment = CancelableAlertDialogFragment.newInstance(
                getString(R.string.confirm_logout_session_title),
                getString(R.string.confirm_logout_message),
                getString(R.string.label_accept),
                getString(R.string.label_cancel),
                R.mipmap.ic_power_settings_new_black_36dp);
        cancelableAlertDialogFragment.show((this).getFragmentManager(), TAG_CLASS);
    }

    @Override
    public void onCancelableAlertDialogPositiveClick(DialogFragment dialog) {
        AppPreferences.getAppPreferences(this).edit().remove(AppPreferences.KEY_PREFERENCE_LOGGED_IN).apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

    }

    @Override
    public void onCancelableAlertDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onItemOrderListenerSelected(Order orders) {

    }

    @Override
    public void onItemMenuSelectedListener(Menu menu) {

    }
}
