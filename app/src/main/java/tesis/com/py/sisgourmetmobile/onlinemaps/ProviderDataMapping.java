package tesis.com.py.sisgourmetmobile.onlinemaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import tesis.com.py.sisgourmetmobile.models.ProviderQualificationModel;

/**
 * Created by Manu0 on 9/10/2017.
 */

public class ProviderDataMapping {

    public static ProviderQualificationModel getProviderDataFromJson(JSONObject data) {
        ProviderQualificationModel pdm = new ProviderQualificationModel();
        try {

            if (data.has("name")) {
                pdm.setProviderName(data.getString("name"));
            }

            if (data.has("max_value")) {
                pdm.setProviderMaxValue(data.getInt("max_value"));
            }

            if (data.has("provider_rating")) {
                pdm.setProviderRating(data.getString("provider_rating"));
            }

            if (data.has("total_users_comments")) {
                pdm.setTotalUserComments(data.getInt("total_users_comments"));
            }

            if (data.has("provider_image")) {
                String providerImage = data.getString("provider_image");
                byte[] decodedString = Base64.decode(providerImage, Base64.DEFAULT);
                pdm.setFileArrayImage(decodedString);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pdm;
    }

}
