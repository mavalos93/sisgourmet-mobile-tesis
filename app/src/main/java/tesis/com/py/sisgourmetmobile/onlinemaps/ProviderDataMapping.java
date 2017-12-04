package tesis.com.py.sisgourmetmobile.onlinemaps;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import tesis.com.py.sisgourmetmobile.entities.ProviderRating;

/**
 * Created by Manu0 on 9/10/2017.
 */

public class ProviderDataMapping {

    public static ProviderRating getProviderDataFromJson(JSONObject data) {
        ProviderRating pdm = new ProviderRating();
        try {

            if (data.has("name")) {
                pdm.setProviderName(data.getString("name"));
            }

            if (data.has("provider_id")) {
                pdm.setProviderId(data.getInt("provider_id"));
            }
            if (data.has("max_value")) {
                pdm.setMaxRating(data.getInt("max_value"));
            }

            if (data.has("provider_rating")) {
                pdm.setProviderRating(data.getString("provider_rating"));
            }

            if (data.has("total_user_comments")) {
                pdm.setTotalUserComments(data.getInt("total_user_comments"));
            }

            if (data.has("provider_image")) {
                String providerImage = data.getString("provider_image");
                byte[] decodedString = Base64.decode(providerImage, Base64.DEFAULT);
                pdm.setProviderImage(decodedString);
            }

            if (data.has("countFiveStar")) {
                pdm.setFiveStar(data.getInt("countFiveStar"));
            }

            if (data.has("countFourStar")) {
                pdm.setFourStar(data.getInt("countFourStar"));
            }

            if (data.has("countTrheeStar")) {
                pdm.setThreeStar(data.getInt("countTrheeStar"));
            }

            if (data.has("countTwoStar")) {
                pdm.setTwoStar(data.getInt("countTwoStar"));
            }
            if (data.has("countOnteStar")) {
                pdm.setOneStar(data.getInt("countOnteStar"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pdm;
    }

}
