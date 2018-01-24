package tesis.com.py.sisgourmetmobile.support;

import android.content.Context;

import java.util.Date;

import tesis.com.py.sisgourmetmobile.activities.SummaryStep;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.SummaryOrder;
import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.SummaryOrderRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.Utils;

/**
 * Created by Manu0 on 12/6/2017.
 */

public class UserControlAmount {

    public static boolean overdaftAcount(Context mContext, String mDebitAmount) {
        boolean status = false;
        try {
            int mAmount = Integer.parseInt(mDebitAmount);
            User user = UserRepository.getUser(mContext);
            if (user != null) {
                if (user.getCurrentAmount() > 0) {
                    if (mAmount > user.getCurrentAmount()) {
                        status = true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static long updateAmount(Context mContext, String mDebitAmount) {
        int currentAmount = 0;
        long mInsertId = 0;
        try {
            User user = UserRepository.getUser(mContext);
            if (user != null) {
                currentAmount = user.getCurrentAmount() - Integer.parseInt(mDebitAmount);
                user.setCurrentAmount(currentAmount);
                mInsertId = UserRepository.store(user);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mInsertId;
    }

    public static void deleteHistoryValue(Context context,long orderId) {
        try {
            SummaryOrder summaryOrder = new SummaryOrder();
            summaryOrder = SummaryOrderRepository.getByLunchId(orderId);
            if (summaryOrder != null) {
                SummaryOrderRepository.getDao().delete(summaryOrder);
                User user = UserRepository.getUser(context);
                if (user != null){
                    user.setCurrentAmount(user.getCurrentAmount()+Integer.parseInt(summaryOrder.getPrice()));
                    UserRepository.store(user);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void saveHistoryData(long orderId, String date, int mDrinkId, int mMainMenuId, int mGarnishId, int mProviderId, String totalAmount) {
        try {

            SummaryOrder mSummaryOrder = new SummaryOrder();
            mSummaryOrder.setOrderId(orderId);
            mSummaryOrder.setDate(date);
            mSummaryOrder.setMonth(Utils.getMonth(new Date()));
            mSummaryOrder.setYear(Utils.getYear(new Date()));
            Drinks drinks = DrinksRepository.getDrinkById(mDrinkId);
            mSummaryOrder.setDrinkDescription((drinks == null ? "Sin Bebida" : drinks.getDescription()));
            Garnish garnish = GarnishRepository.getGarnishById(mGarnishId);
            mSummaryOrder.setGarnishDescription((garnish == null ? "Sin Guarnición" : garnish.getDescription()));
            Lunch lunch = LunchRepository.getLunchById(mMainMenuId);
            mSummaryOrder.setOrderDescription((lunch == null ? "Sin Menú principal" : lunch.getMainMenuDescription()));
            Provider provider = ProviderRepository.getProviderById(mProviderId);
            mSummaryOrder.setProvider((provider == null ? "Sin proveedor" : provider.getProviderName()));
            mSummaryOrder.setImage((lunch == null ? null : lunch.getImageMenu()));
            mSummaryOrder.setPrice(String.valueOf(totalAmount));
            SummaryOrderRepository.store(mSummaryOrder);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
