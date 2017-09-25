package tesis.com.py.sisgourmetmobile.onlinemaps;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import tesis.com.py.sisgourmetmobile.entities.Comments;
import tesis.com.py.sisgourmetmobile.models.ProviderQualificationModel;

/**
 * Created by Manu0 on 9/10/2017.
 */

public class CommentsDataMapping {

    public static Comments getCommentsDataFromJson(JSONObject data) {
        Comments comments = new Comments();
        try {

            if (data.has("comment_id")) {
                comments.setId(data.getLong("comment_id"));
            }

            if (data.has("comment_rating")) {
                comments.setRatingValue(data.getInt("comment_rating"));
            }

            if (data.has("description")) {
                comments.setCommentDescription(data.getString("description"));
            }


            if (data.has("lunch_selected_description")) {
                comments.setLunchPackageDescription(data.getString("lunch_selected_description"));
            }

            if (data.has("user_name")) {
                comments.setUserName(data.getString("user_name"));
            }

            if (data.has("date_comment")) {
                comments.setDateComment(data.getString("date_comment"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
