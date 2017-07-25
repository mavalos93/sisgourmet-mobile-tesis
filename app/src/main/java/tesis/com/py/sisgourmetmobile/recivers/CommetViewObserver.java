package tesis.com.py.sisgourmetmobile.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import tesis.com.py.sisgourmetmobile.loaders.CommetViewLoader;

/**
 * Created by Manu0 on 1/6/2017.
 */

public class CommetViewObserver  extends BroadcastReceiver {


    public static final String ACTION_LOAD_COMMENT = "tesis.com.py.sisgourmetmobile.LOAD_COMMENT";
    private CommetViewLoader mLoader;

    public CommetViewObserver(CommetViewLoader loader) {
        mLoader = loader;
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ACTION_LOAD_COMMENT);
        mLoader.getContext().registerReceiver(this, mFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mLoader.onContentChanged();
    }
}
