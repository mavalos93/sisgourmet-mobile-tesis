package tesis.com.py.sisgourmetmobile.repositories;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.OrderDao;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by mavalos on 23/02/17.
 */

public class OrderRepository {

    public static void store(Order order) {
        getDao().insertOrReplace(order);
    }

    public static List<Order> getAllOrder() {
        return getDao().queryBuilder()
                .list();
    }

    public static long count() {
        return getDao().queryBuilder().count();
    }

    private static OrderDao getDao() {
        return MainSession.getDaoSession().
                getOrderDao();
    }
}

