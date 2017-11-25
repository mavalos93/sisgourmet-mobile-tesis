package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.entities.CommentsDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 19/7/2017.
 */

public class AllCommentsRepository {

    public static void store(Comments comments) {
        getDao().insertOrReplace(comments);
    }

    public static List<Comments> getAll() {
        return getDao().queryBuilder()
                .list();
    }


    public static void clearAll() {
        getDao().deleteAll();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }

    private static CommentsDao getDao() {
        return MainSession.getDaoSession().
                getCommentsDao();
    }

    public static long countCommentByProviderId(int mProviderId) {
        return getDao().queryBuilder().where(CommentsDao.Properties.ProviderId.eq(mProviderId)).count();
    }
}
