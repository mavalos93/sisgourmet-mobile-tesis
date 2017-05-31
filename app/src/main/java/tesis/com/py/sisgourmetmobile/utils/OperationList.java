package tesis.com.py.sisgourmetmobile.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.ResetPassActivity;


/**
 * Created by diego on 14/10/16.
 */

public class OperationList {
    private List<Operation> mOperations;
    private Context mContext;
    private final String OPERATION = "Operation";

    public OperationList(Context context) {
        mContext = context;
        mOperations = new ArrayList<>();
    }

    public List<Operation> getOperations() {




        Operation opResetPassword = new Operation(4,
                mContext.getString(R.string.reset_password_menu),
                mContext.getString(R.string.reset_password_menu_description),
                ResetPassActivity.class,
                OPERATION,
                R.mipmap.ic_lock_black_36dp);
        mOperations.add(opResetPassword);

        return mOperations;
    }

    public class Operation {
        private int mItemNumber;
        private String mItemName;
        private String mItemDescription;
        private Class<?> mItemClass;
        private String mType;
        private int mIcon;


        public Operation(int mItemNumber, String mItemName, String mItemDescription, Class<?> mItemClass, String mType, int mIcon) {
            this.mItemNumber = mItemNumber;
            this.mItemName = mItemName;
            this.mItemDescription = mItemDescription;
            this.mItemClass = mItemClass;
            this.mType = mType;
            this.mIcon = mIcon;
        }

        public int getmIcon() {
            return mIcon;
        }

        public Operation setmIcon(int mIcon) {
            this.mIcon = mIcon;
            return this;
        }


        public int getItemNumber() {
            return mItemNumber;
        }

        public Operation setItemNumber(int mItemNumber) {
            this.mItemNumber = mItemNumber;
            return this;
        }

        public String getItemName() {
            return mItemName;
        }

        public Operation setItemName(String mItemName) {
            this.mItemName = mItemName;
            return this;
        }

        public Class<?> getItemClass() {
            return mItemClass;
        }

        public Operation setItemClass(Class<?> mItemClass) {
            this.mItemClass = mItemClass;
            return this;
        }

        public String getType() {
            return mType;
        }

        public Operation setType(String mType) {
            this.mType = mType;
            return this;
        }

        public String getmItemDescription() {
            return mItemDescription;
        }

        public void setmItemDescription(String mItemDescription) {
            this.mItemDescription = mItemDescription;
        }
    }
}
