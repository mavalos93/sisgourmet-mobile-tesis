package tesis.com.py.sisgourmetmobile.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import tesis.com.py.sisgourmetmobile.loaders.OrderLoader;

/**
 * Created by mavalos on 23/02/17.
 */

public class OrdersObserver extends BroadcastReceiver {


    public static final String ACTION_LOAD_ORDERS = "tesis.com.py.sisgourmetmobile.LOAD_ORDERS";
    private OrderLoader mLoader;

    public OrdersObserver(OrderLoader loader) {
        mLoader = loader;
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ACTION_LOAD_ORDERS);
        mLoader.getContext().registerReceiver(this, mFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mLoader.onContentChanged();
    }
}
