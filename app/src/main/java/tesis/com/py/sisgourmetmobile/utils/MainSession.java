package tesis.com.py.sisgourmetmobile.utils;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import tesis.com.py.sisgourmetmobile.entities.DaoMaster;
import tesis.com.py.sisgourmetmobile.entities.DaoSession;

/**
 * Created by Manu0 on 22/1/2017.
 */

public class MainSession extends Application {
    private static MainSession INSTANCE = null;
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "sisgourmetdb", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static MainSession getInstance(){
        return INSTANCE;
    }
    public static  DaoSession getDaoSession() {
        return getInstance().daoSession;
    }
}

