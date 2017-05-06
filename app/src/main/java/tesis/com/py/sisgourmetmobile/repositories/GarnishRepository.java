package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.GarnishDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 6/5/2017.
 */

public class GarnishRepository {


    public static void store(Garnish garnish) {
        getDao().insertOrReplace(garnish);
    }

    public static List<Garnish> getAllGarnish() {
        return getDao().queryBuilder()
                .list();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }


    public static List<Garnish> getGarnishByLunchId(long lunchId) {

        return getDao().queryBuilder().where(GarnishDao.Properties.LunchId.eq(lunchId)).list();

    }

    private static GarnishDao getDao() {
        return MainSession.getDaoSession().getGarnishDao();
    }
}
