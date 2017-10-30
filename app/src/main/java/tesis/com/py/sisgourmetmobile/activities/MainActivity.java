package tesis.com.py.sisgourmetmobile.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import py.com.library.style.TabStepper;
import py.com.library.util.LinearityChecker;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.dialogs.CancelableAlertDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.fragments.MenuFragment;
import tesis.com.py.sisgourmetmobile.fragments.MyCommentsFragment;
import tesis.com.py.sisgourmetmobile.fragments.OrdersFragment;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;

public class MainActivity extends AppCompatActivity
        implements
        MenuFragment.OnItemMenuListener, MyCommentsFragment.OnItemMyCommentsListenerSelected,
        OrdersFragment.OnItemOrderListenerSelected,
        CancelableAlertDialogFragment.CancelableAlertDialogFragmentListener {
    private Toolbar toolbar;
    private final String TAG_CLASS = MainActivity.class.getName();
    private MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupFragment(MenuFragment.newInstance());

        //Initializing the bottomNavigationView
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_selected_menu:
                        setupFragment(MenuFragment.newInstance());
                        break;
                    case R.id.action_my_orders:
                        setupFragment(OrdersFragment.newInstance());
                        break;
                    case R.id.action_my_favorites:
                        setupFragment(MyCommentsFragment.newInstance());
                        break;
                }

                return true;
            }
        });

    }


    private void setupFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().
                beginTransaction();
        transaction.replace(R.id.rootLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        TabStepper.isDone = false;
        LinearityChecker.mDone.clear();
        StepLunch.radioGarnishId = 0;
        StepLunch.typeLunchCase = 0;
        StepDrinks.mDrinkId = 0;
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
        AppPreferences.getAppPreferences(this).edit().clear().apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

    }

    @Override
    public void onCancelableAlertDialogNegativeClick(DialogFragment dialog) {

    }


    @Override
    public void onItemMenuSelectedListener(Menu menu) {

    }

    @Override
    public void onItemOrderListenerSelected(Order orders) {

    }

    @Override
    public void onItemMyCommentsListenerSelected(Qualification qualification) {

    }
}
