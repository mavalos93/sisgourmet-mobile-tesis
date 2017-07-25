package tesis.com.py.sisgourmetmobile.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import tesis.com.py.sisgourmetmobile.loaders.MyCommentsLoader;
import tesis.com.py.sisgourmetmobile.loaders.OrderLoader;

/**
 * Created by Manu0 on 11/7/2017.
 */

public class MyCommentsObserver extends BroadcastReceiver {


    public static final String ACTION_LOAD_MY_COMMENTS = "tesis.com.py.sisgourmetmobile.LOAD_MY_COMMENTS";
    private MyCommentsLoader mLoader;

    public MyCommentsObserver(MyCommentsLoader loader) {
        mLoader = loader;
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ACTION_LOAD_MY_COMMENTS);
        mLoader.getContext().registerReceiver(this, mFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mLoader.onContentChanged();
    }
}
