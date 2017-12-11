package tesis.com.py.sisgourmetmobile.repositories;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.OrderDao;
import tesis.com.py.sisgourmetmobile.recivers.OrdersObserver;
import tesis.com.py.sisgourmetmobile.utils.MainSession;

/**
 * Created by mavalos on 23/02/17.
 */

public class OrderRepository {


    public static long store(Order order) {
        long id = getDao().insertOrReplace(order);
        return id;
    }

    public static List<Order> getAllOrder() {
        return getDao().queryBuilder()
                .list();
    }

    public static Order getById(long id) {
        return getDao().queryBuilder().where(OrderDao.Properties.Id.eq(id)).unique();
    }


    public static long count() {
        return getDao().queryBuilder().count();
    }

    private static OrderDao getDao() {
        return MainSession.getDaoSession().
                getOrderDao();
    }

    public static void updateOrderTransaction(Context mContext, long orderId, int orderTransactionId, int status, String message) {

        long mTransactionId = 0;
        Order mOrderObject = getById(orderId);
        if (mOrderObject != null) {
            mOrderObject.setTransactionOrderId(orderTransactionId);
            mOrderObject.setStatusOrder(status);
            mOrderObject.setObservation(message);
            mTransactionId = OrderRepository.store(mOrderObject);
        }

        if (mTransactionId > 0) {
            mContext.sendBroadcast(new Intent(OrdersObserver.ACTION_LOAD_ORDERS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }
}

