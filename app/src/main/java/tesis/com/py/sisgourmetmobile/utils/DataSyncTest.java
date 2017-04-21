package tesis.com.py.sisgourmetmobile.utils;

import android.content.Context;
import android.support.v7.util.ListUpdateCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tesis.com.py.sisgourmetmobile.adapters.ProviderRecyclerViewAdapter;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.repositories.DrinksRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;

/**
 * Created by Manu0 on 4/2/2017.
 */

public class DataSyncTest {

    public static List<Provider> setProviderData(Context context) {


        Provider providerObject1 = new Provider();
        providerObject1.setId(1L);
        providerObject1.setProviderName("Coca Cola");
        providerObject1.setProviderType("Bebidas");

        Provider providerObject2 = new Provider();
        providerObject2.setId(2L);
        providerObject2.setProviderName("La Vienesa");
        providerObject2.setProviderType("Menu Principal");

        Provider providerObject3 = new Provider();
        providerObject3.setId(3L);
        providerObject3.setProviderName("Ña Eustaquia");
        providerObject3.setProviderType("Guarnicion");

        List<Provider> providerList = new ArrayList<>();
        providerList.add(providerObject1);
        providerList.add(providerObject2);
        providerList.add(providerObject3);

        for (Provider provider : providerList) {
            ProviderRepository.store(provider);
        }
        return providerList;
    }


    public static List<Lunch> setMenuData() {
        Lunch lunch1 = new Lunch();
        lunch1.setId(1L);
        lunch1.setProviderId(2L);
        lunch1.setMainMenuDescription("Bife a la plancha");
        lunch1.setGarnishDescription("Ensalada Mixta");
        lunch1.setPriceUnit(20000);
        lunch1.setMenuDate(new Date());
        lunch1.setRaitingMenu(1L);

        Lunch lunch2 = new Lunch();
        lunch2.setId(2L);
        lunch2.setProviderId(2L);
        lunch2.setMainMenuDescription("Grillé de pollo");
        lunch2.setGarnishDescription("Puré de papas");
        lunch2.setPriceUnit(19000);
        lunch2.setMenuDate(new Date());
        lunch2.setRaitingMenu(2L);


        Lunch lunch3 = new Lunch();
        lunch3.setId(3L);
        lunch3.setProviderId(2L);
        lunch3.setMainMenuDescription("Marinera de Carne");
        lunch3.setGarnishDescription("Ensalada rusa");
        lunch3.setPriceUnit(19000);
        lunch3.setMenuDate(new Date());
        lunch3.setRaitingMenu(3L);


        Lunch lunch4 = new Lunch();
        lunch4.setId(4L);
        lunch4.setProviderId(2L);
        lunch4.setMainMenuDescription("Tarta de pollo");
        lunch4.setGarnishDescription("Ensalada verde");
        lunch4.setPriceUnit(20000);
        lunch4.setMenuDate(new Date());
        lunch4.setRaitingMenu(4L);


        Lunch lunch5 = new Lunch();
        lunch5.setId(5L);
        lunch5.setProviderId(2L);
        lunch5.setMainMenuDescription("Picadito de Carne");
        lunch5.setGarnishDescription("Ensalada verde");
        lunch5.setPriceUnit(20000);
        lunch5.setMenuDate(new Date());
        lunch5.setRaitingMenu(5L);


        List<Lunch> lunchList = new ArrayList<>();
        lunchList.add(lunch1);
        lunchList.add(lunch2);
        lunchList.add(lunch3);
        lunchList.add(lunch4);
        lunchList.add(lunch5);


        for (Lunch lunch : lunchList) {
            LunchRepository.store(lunch);
        }

        return lunchList;
    }


    public static List<Drinks> setDrinks() {
        Drinks drinks1 = new Drinks();
        drinks1.setId(1L);
        drinks1.setPriceUnit(2000);
        drinks1.setCurrentStock(10);
        drinks1.setDescription("Puro sol 200 ml.");
        drinks1.setMinimunStock(2);
        drinks1.setProvider("Pulp S.A");

        Drinks drinks2 = new Drinks();
        drinks2.setId(2L);
        drinks2.setPriceUnit(2000);
        drinks2.setCurrentStock(10);
        drinks2.setDescription("Frugos de Naranja 200 ml.");
        drinks2.setMinimunStock(2);
        drinks2.setProvider("Coca Cola Company");



        Drinks drinks3 = new Drinks();
        drinks3.setId(3L);
        drinks3.setPriceUnit(3000);
        drinks3.setCurrentStock(10);
        drinks3.setDescription("Coca Cola 200 ml.");
        drinks3.setMinimunStock(2);
        drinks3.setProvider("Coca Cola Company");



        Drinks drinks4 = new Drinks();
        drinks4.setId(4L);
        drinks4.setPriceUnit(3000);
        drinks4.setCurrentStock(10);
        drinks4.setDescription("Guaraná 200 ml.");
        drinks4.setMinimunStock(2);
        drinks4.setProvider("Coca Cola Company");


        Drinks drinks5 = new Drinks();
        drinks5.setId(5L);
        drinks5.setPriceUnit(5000);
        drinks5.setCurrentStock(10);
        drinks5.setDescription("Purifrú 500 ml.");
        drinks5.setMinimunStock(2);
        drinks5.setProvider("Proveedor S.A.");

        List<Drinks> drinksList = new ArrayList<>();
        drinksList.add(drinks1);
        drinksList.add(drinks2);
        drinksList.add(drinks3);
        drinksList.add(drinks4);
        drinksList.add(drinks5);



        for (Drinks drink : drinksList) {
            DrinksRepository.store(drink);
        }

        return drinksList;
    }
}
