package tesis.com.py.sisgourmetmobile.repositories;

import android.content.Context;
import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.entities.UsersDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 22/1/2017.
 */

public class UsersRepository {

    public static void clearAll() {
        getDao().deleteAll();
    }


    public static long count() {
        return getDao().queryBuilder().count();
    }


    public static long store(Users users) {
        return getDao().insertOrReplace(users);
    }

    public static Users controlUser(String username, String password, String identifyCard) {
        return getDao().queryBuilder()
                .where(UsersDao.Properties.UserName.eq(username))
                .where(UsersDao.Properties.Password.eq(password))
                .where(UsersDao.Properties.IdentifyCardNumber.eq(identifyCard)).unique();
    }


    public static Users loginControlQuery(String username, String password) {
        return getDao().queryBuilder()
                .where(UsersDao.Properties.UserName.eq(username))
                .where(UsersDao.Properties.Password.eq(password)).unique();
    }

    public static Users getUserById(long userId) {
        return getDao().queryBuilder().where(UsersDao.Properties.Id.eq(userId)).unique();
    }

    private static UsersDao getDao() {
        return MainSession.getDaoSession().
                getUsersDao();
    }


}
