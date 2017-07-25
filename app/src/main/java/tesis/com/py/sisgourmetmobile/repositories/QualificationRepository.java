package tesis.com.py.sisgourmetmobile.repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.entities.QualificationDao;
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

    public static QualificationDao getDao() {
        return MainSession.getDaoSession().
                getQualificationDao();
    }
}
