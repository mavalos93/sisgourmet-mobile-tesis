package tesis.com.py.sisgourmetmobile.support;

import android.content.Context;

import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;

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
}
