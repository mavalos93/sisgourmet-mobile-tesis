package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.ProviderRating;
import tesis.com.py.sisgourmetmobile.entities.ProviderRatingDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 11/25/2017.
 */

public class ProviderRatingRepository {


    public static List<ProviderRating> getAll() {
        return getDao().queryBuilder().list();
    }

    public static long store(ProviderRating providerRating) {
        return getDao().insertOrReplace(providerRating);
    }

    public static long cont() {
        return getDao().queryBuilder().count();
    }

    public static void clearAll() {
        getDao().deleteAll();
    }

    public static ProviderRatingDao getDao() {
        return MainSession.getDaoSession().getProviderRatingDao();
    }
}
