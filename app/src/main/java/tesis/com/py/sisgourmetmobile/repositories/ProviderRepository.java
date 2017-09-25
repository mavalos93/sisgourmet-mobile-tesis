package tesis.com.py.sisgourmetmobile.repositories;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.ProviderDao;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.entities.UsersDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 4/2/2017.
 */

public class ProviderRepository {

    public static void store(Provider provider) {
        getDao().insertOrReplace(provider);
    }

    public static List<Provider> getAllProvider() {
        return getDao().queryBuilder()
                .list();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }


    public static Provider getProviderById(long providerId) {

        return getDao().queryBuilder().where(ProviderDao.Properties.Id.eq(providerId)).unique();

    }

    public static ProviderDao getDao() {
        return MainSession.getDaoSession().
                getProviderDao();
    }

}
