package tesis.com.py.sisgourmetmobile.repositories;

import android.content.Context;

import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.entities.UserDao;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 11/8/2017.
 */
public class UserRepository {

    public static void clearAll() {
        getDao().deleteAll();
    }


    public static long count() {
        return getDao().queryBuilder().count();
    }


    public static long store(User User) {
        return getDao().insertOrReplace(User);
    }




    public static User getUserById(long userId) {
        return getDao().queryBuilder().where(UserDao.Properties.Id.eq(userId)).unique();
    }

    private static UserDao getDao() {
        return MainSession.getDaoSession().getUserDao();
    }

    public static User getUser(Context context) {
        return UserRepository.getUserById(AppPreferences.getAppPreferences(context).getLong(AppPreferences.KEY_USER_ID, 0));
    }

}
