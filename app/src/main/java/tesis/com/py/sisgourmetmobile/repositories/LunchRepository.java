package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.LunchDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 16/4/2017.
 */

public class LunchRepository {



    public static void store(Lunch lunch) {
        getDao().insertOrReplace(lunch);
    }

    public static List<Lunch> getAllLunch() {
        return getDao().queryBuilder()
                .list();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }

    public static List<Lunch> getMenuByProviderId(long providerId){
        return getDao().queryBuilder().where(LunchDao.Properties.ProviderId.eq(providerId)).list();
    }
    private static LunchDao getDao() {
        return MainSession.getDaoSession().
                getLunchDao();
    }
}
