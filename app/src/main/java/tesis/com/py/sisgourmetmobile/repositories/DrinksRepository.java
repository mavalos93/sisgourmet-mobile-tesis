package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.DrinksDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 18/4/2017.
 */

public class DrinksRepository {

    public static void store(Drinks drinks) {
        getDao().insertOrReplace(drinks);
    }

    public static List<Drinks> getAllDrinks() {
        return getDao().queryBuilder()
                .list();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }

    private static DrinksDao getDao() {
        return MainSession.getDaoSession().
                getDrinksDao();
    }
}
