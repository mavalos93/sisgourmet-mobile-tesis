package tesis.com.py.sisgourmetmobile.utils;

import java.util.ArrayList;
import java.util.Date;
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
 * Created by Manu0 on 4/2/2017.
 */

public class DataSyncTest {

    public static void setProviderData() {


        Provider providerObject2 = new Provider();
        providerObject2.setId(1L);
        providerObject2.setProviderName("La Vienesa");
        providerObject2.setProviderType("Menu Principal");

        Provider providerObject3 = new Provider();
        providerObject3.setId(2L);
        providerObject3.setProviderName("Ña Eustaquia");
        providerObject3.setProviderType("Guarnicion");

        Provider providerObject4 = new Provider();
        providerObject4.setId(3L);
        providerObject4.setProviderName("Bolsi");
        providerObject4.setProviderType("Menu principal");

        List<Provider> providerList = new ArrayList<>();
        providerList.add(providerObject2);
        providerList.add(providerObject3);
        providerList.add(providerObject4);

        for (Provider provider : providerList) {
            ProviderRepository.store(provider);
        }
    }


    public static void setMenuData() {
        Lunch lunch1 = new Lunch();
        lunch1.setId(1L);
        lunch1.setProviderId(1L);
        lunch1.setMainMenuDescription("Bife a la plancha");
        lunch1.setPriceUnit(20000);
        lunch1.setMenuDate(new Date());
        lunch1.setRatingMenu(1L);

        Lunch lunch2 = new Lunch();
        lunch2.setId(2L);
        lunch2.setProviderId(1L);
        lunch2.setMainMenuDescription("Grillé de pollo");
        lunch2.setPriceUnit(19000);
        lunch2.setMenuDate(new Date());
        lunch2.setRatingMenu(2L);


        Lunch lunch3 = new Lunch();
        lunch3.setId(3L);
        lunch3.setProviderId(1L);
        lunch3.setMainMenuDescription("Marinera de Carne");
        lunch3.setPriceUnit(19000);
        lunch3.setMenuDate(new Date());
        lunch3.setRatingMenu(3L);


        Lunch lunch4 = new Lunch();
        lunch4.setId(4L);
        lunch4.setProviderId(2L);
        lunch4.setMainMenuDescription("Tarta de pollo");
        lunch4.setPriceUnit(20000);
        lunch4.setMenuDate(new Date());
        lunch4.setRatingMenu(4L);


        Lunch lunch5 = new Lunch();
        lunch5.setId(5L);
        lunch5.setProviderId(2L);
        lunch5.setMainMenuDescription("Picadito de Carne");
        lunch5.setPriceUnit(20000);
        lunch5.setMenuDate(new Date());
        lunch5.setRatingMenu(5L);


        Lunch lunch6 = new Lunch();
        lunch6.setId(6L);
        lunch6.setProviderId(2L);
        lunch6.setMainMenuDescription("Romanitas de pollo");
        lunch6.setPriceUnit(20000);
        lunch6.setMenuDate(new Date());
        lunch6.setRatingMenu(5L);

        Lunch lunch7 = new Lunch();
        lunch7.setId(7L);
        lunch7.setProviderId(3L);
        lunch7.setMainMenuDescription("Ensalada Pescador");
        lunch7.setPriceUnit(20000);
        lunch7.setMenuDate(new Date());
        lunch7.setRatingMenu(5L);

        Lunch lunch8 = new Lunch();
        lunch8.setId(8L);
        lunch8.setProviderId(3L);
        lunch8.setMainMenuDescription("Lasagna de carne");
        lunch8.setPriceUnit(25000);
        lunch8.setMenuDate(new Date());
        lunch8.setRatingMenu(5L);

        List<Lunch> lunchList = new ArrayList<>();
        lunchList.add(lunch1);
        lunchList.add(lunch2);
        lunchList.add(lunch3);
        lunchList.add(lunch4);
        lunchList.add(lunch5);
        lunchList.add(lunch6);
        lunchList.add(lunch7);
        lunchList.add(lunch8);


        for (Lunch lunch : lunchList) {
            LunchRepository.store(lunch);
        }

    }


    public static void setDrinks() {
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

    }


    public static void setGarnish() {
        Garnish garnish1 = new Garnish();
        garnish1.setId(1L);
        garnish1.setLunchId(1L);
        garnish1.setDescription("Ensalada Mixta");
        garnish1.setUnitPrice(7000);

        Garnish garnish2 = new Garnish();
        garnish2.setId(2L);
        garnish2.setLunchId(1L);
        garnish2.setDescription("Puré de papas");
        garnish2.setUnitPrice(10000);


        Garnish garnish3 = new Garnish();
        garnish3.setId(3L);
        garnish3.setLunchId(1L);
        garnish3.setDescription("Ensalada de poroto");
        garnish3.setUnitPrice(8500);

        Garnish garnish4 = new Garnish();
        garnish4.setId(4L);
        garnish4.setLunchId(4L);
        garnish4.setDescription("Ensalada verde");
        garnish4.setUnitPrice(5000);

        Garnish garnish5 = new Garnish();
        garnish5.setId(5L);
        garnish5.setLunchId(5L);
        garnish5.setDescription("Sopa Paraguaya");
        garnish5.setUnitPrice(7000);


        Garnish garnish6 = new Garnish();
        garnish6.setId(6L);
        garnish6.setLunchId(6L);
        garnish6.setDescription("Fideo a la manteca");
        garnish6.setUnitPrice(6000);


        Garnish garnish7 = new Garnish();
        garnish7.setId(7L);
        garnish7.setLunchId(2L);
        garnish7.setDescription("Arroz quesú");
        garnish7.setUnitPrice(7000);


        Garnish garnish8 = new Garnish();
        garnish8.setId(8L);
        garnish8.setLunchId(3L);
        garnish8.setDescription("Ensalada de tomates");
        garnish8.setUnitPrice(8000);


        Garnish garnish9 = new Garnish();
        garnish9.setId(9L);
        garnish9.setLunchId(3L);
        garnish9.setDescription("Ensalada de Arroz");
        garnish9.setUnitPrice(6500);


        Garnish garnish10 = new Garnish();
        garnish10.setId(10L);
        garnish10.setLunchId(3L);
        garnish10.setDescription("Ensalada de Repollo");
        garnish10.setUnitPrice(6500);


        Garnish garnish11 = new Garnish();
        garnish11.setId(11L);
        garnish11.setLunchId(7L);
        garnish11.setDescription("Ensalada de papas");
        garnish11.setUnitPrice(6500);


        Garnish garnish12 = new Garnish();
        garnish12.setId(12L);
        garnish12.setLunchId(7L);
        garnish12.setDescription("Papas fritas");
        garnish12.setUnitPrice(6500);


        Garnish garnish13 = new Garnish();
        garnish13.setId(13L);
        garnish13.setLunchId(8L);
        garnish13.setDescription("Arroz a la crema");
        garnish13.setUnitPrice(6500);

        Garnish garnish14 = new Garnish();
        garnish14.setId(14L);
        garnish14.setLunchId(8L);
        garnish14.setDescription("Ensalada Bolsi");
        garnish14.setUnitPrice(6500);

        List<Garnish> garnishList = new ArrayList<>();
        garnishList.add(garnish1);
        garnishList.add(garnish2);
        garnishList.add(garnish3);
        garnishList.add(garnish4);
        garnishList.add(garnish5);
        garnishList.add(garnish6);
        garnishList.add(garnish7);
        garnishList.add(garnish8);
        garnishList.add(garnish9);
        garnishList.add(garnish10);
        garnishList.add(garnish11);
        garnishList.add(garnish12);
        garnishList.add(garnish13);
        garnishList.add(garnish14);


        for (Garnish gn : garnishList) {
            GarnishRepository.store(gn);
        }


    }
}
