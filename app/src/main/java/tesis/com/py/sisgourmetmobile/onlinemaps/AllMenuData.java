package tesis.com.py.sisgourmetmobile.onlinemaps;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.models.ProviderQualificationModel;

/**
 * Created by Manu0 on 9/24/2017.
 */

public class AllMenuData {

    public static Lunch getMainMenuData(JSONObject data) {
        Lunch lunchObject = new Lunch();
        try {

            if (data.has("codigoMenuPrincipal")) {
                lunchObject.setPrincipalMenuCode(data.getInt("codigoMenuPrincipal"));
            }

            if (data.has("precioUnitario")) {
                lunchObject.setPriceUnit(data.getInt("precioUnitario"));
            }

            if (data.has("menuPrincipalDescripcion")) {
                lunchObject.setMainMenuDescription(data.getString("menuPrincipalDescripcion"));
            }

            if (data.has("codigoProveedor")) {
                lunchObject.setProviderId(data.getInt("codigoProveedor"));
            }

            if (data.has("fechaMenu")) {
                lunchObject.setMenuDate(data.getString("fechaMenu"));
            }
            if (data.has("ratingMenu")) {
                lunchObject.setRatingMenu(data.getDouble("ratingMenu"));
            }

            if (data.has("imageMenu")) {
                String menuImage = data.getString("imageMenu");
                byte[] decodedString = Base64.decode(menuImage, Base64.DEFAULT);
                lunchObject.setImageMenu(decodedString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lunchObject;
    }


    public static Provider getProviderData(JSONObject data) {
        Provider providerObject = new Provider();
        try {

            if (data.has("id")) {
                providerObject.setProviderId(data.getInt("id"));
            }

            if (data.has("description")) {
                providerObject.setProviderName(data.getString("description"));
            }

            if (data.has("provider_image")) {
                String menuImage = data.getString("provider_image");
                byte[] decodedString = Base64.decode(menuImage, Base64.DEFAULT);
                providerObject.setProviderImage(decodedString);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return providerObject;
    }


    public static Garnish getGarnishData(JSONObject data) {
        Garnish garnishObject = new Garnish();
        try {

            if (data.has("codigoMenuPrincipal")) {
                garnishObject.setLunchId(data.getInt("codigoMenuPrincipal"));
            }

            if (data.has("codigoGuarnicion")) {
                garnishObject.setGarnishId(data.getInt("codigoGuarnicion"));
            }

            if (data.has("descripcion")) {
                garnishObject.setDescription(data.getString("descripcion"));
            }


            if (data.has("precioUnitario")) {
                garnishObject.setUnitPrice(data.getInt("precioUnitario"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return garnishObject;
    }

    public static Drinks getDrinkData(JSONObject data) {
        Drinks drinkObject = new Drinks();
        try {

            if (data.has("codigoBebida")) {
                drinkObject.setDrinkId(data.getInt("codigoBebida"));
            }

            if (data.has("descripcion")) {
                drinkObject.setDescription(data.getString("descripcion"));
            }

            if (data.has("stockActual")) {
                drinkObject.setCurrentStock(data.getInt("stockActual"));
            }


            if (data.has("pecioUnitario")) {
                drinkObject.setPriceUnit(data.getInt("pecioUnitario"));
            }

            if (data.has("providerDescripcion")) {
                drinkObject.setProvider(data.getString("providerDescripcion"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return drinkObject;
    }


}
