package tesis.com.py.sisgourmetmobile.repositories;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.entities.QualificationDao;
import tesis.com.py.sisgourmetmobile.recivers.MyCommentsObserver;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 11/2/2017.
 */

public class QualificationRepository {

    public static long store(Qualification qualification) {
        return getDao().insertOrReplace(qualification);
    }

    public static List<Qualification> getQualificationDesc() {
        return getDao().queryBuilder().orderDesc(QualificationDao.Properties.Order).list();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }

    public static Qualification getById(long id) {
        return getDao().queryBuilder().where(QualificationDao.Properties.Id.eq(id)).unique();
    }

    public static void updateCommentTransaction(Context mContext, long commentId, int status, String message) {

        long mTransactionId = 0;
        Qualification mQualificationObject = getById(commentId);
        if (mQualificationObject != null) {
            mQualificationObject.setStatusSend(status);
            mQualificationObject.setObservation(message);
            mTransactionId = QualificationRepository.store(mQualificationObject);
        }

        if (mTransactionId > 0) {
            mContext.sendBroadcast(new Intent(MyCommentsObserver.ACTION_LOAD_MY_COMMENTS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    public static QualificationDao getDao() {
        return MainSession.getDaoSession().
                getQualificationDao();
    }
}
