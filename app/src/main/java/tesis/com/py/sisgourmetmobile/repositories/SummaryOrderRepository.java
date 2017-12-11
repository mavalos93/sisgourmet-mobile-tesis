package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.SummaryOrder;
import tesis.com.py.sisgourmetmobile.entities.SummaryOrderDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by Manu0 on 12/11/2017.
 */

public class SummaryOrderRepository {


    public static long store(SummaryOrder summaryOrder) {
        return getDao().insertOrReplace(summaryOrder);
    }

    public static List<SummaryOrder> getDataByYearAndMonth(int month, int year) {
        return getDao().queryBuilder().where(SummaryOrderDao.Properties.Month.eq(month))
                .where(SummaryOrderDao.Properties.Year.eq(year)).list();
    }

    public static SummaryOrder getByLunchId(long orderId) {
        return getDao().queryBuilder().where(SummaryOrderDao.Properties.OrderId.eq(orderId)).unique();
    }

    public static SummaryOrderDao getDao() {
        return MainSession.getDaoSession().getSummaryOrderDao();
    }
}
