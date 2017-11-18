package tesis.com.py.sisgourmetmobile.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;

/**
 * Created by Manu0 on 9/24/2017.
 */

public class SaveDataAsyncTask extends AsyncTask<Void, Void, Boolean> {

    public interface AsyncResponse {
        void processFinish(Boolean status);
    }

    AsyncResponse delegate = null;
    private Context mContext;
    private List<Provider> mProviderList = new ArrayList<>();
    private List<Lunch> mLunchList = new ArrayList<>();
    private List<Garnish> mGarnishList = new ArrayList<>();
    private List<Drinks> mDrinkList = new ArrayList<>();


    public SaveDataAsyncTask(Context context, AsyncResponse delegate, List<Provider> providerList, List<Lunch> lunchList, List<Garnish> garnishList, List<Drinks> drinksList) {
        this.mContext = context;
        this.mProviderList = providerList;
        this.delegate = delegate;
        this.mLunchList = lunchList;
        this.mGarnishList = garnishList;
        this.mDrinkList = drinksList;
    }

    @Override
    protected void onPreExecute() {
        ProviderRepository.getDao().deleteAll();
        LunchRepository.getDao().deleteAll();
        GarnishRepository.getDao().deleteAll();
        DrinksRepository.getDao().deleteAll();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        int providerCount = 0;
        int lunchCount = 0;
        int garnishCount = 0;
        int drinksCount = 0;
        boolean status;

        for (Provider provider : mProviderList) {
            providerCount++;
            ProviderRepository.store(provider);
        }
        for (Lunch lunch : mLunchList) {
            lunchCount++;
            LunchRepository.store(lunch);
        }
        for (Garnish garnish : mGarnishList) {
            garnishCount++;
            GarnishRepository.store(garnish);
        }
        for (Drinks drinks : mDrinkList) {
            drinksCount++;
            DrinksRepository.store(drinks);
        }

        status = providerCount == mProviderList.size() && lunchCount == mLunchList.size() && garnishCount == mGarnishList.size() && drinksCount == mDrinkList.size();

        return status;
    }


    @Override
    protected void onPostExecute(Boolean status) {
        delegate.processFinish(status);
    }
}
