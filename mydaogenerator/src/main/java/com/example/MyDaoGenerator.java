package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "tesis.com.py.sisgourmetmobile.entities");

        Entity persona = schema.addEntity("Person");
        persona.addIdProperty();
        persona.addStringProperty("identyCard");
        persona.addStringProperty("ruc");
        persona.addStringProperty("name");
        persona.addStringProperty("lastName");
        persona.addStringProperty("locationAddress");
        persona.addStringProperty("privatePhone");
        persona.addStringProperty("mobilePhone");
        persona.addStringProperty("mailAddress");
        persona.addBooleanProperty("nutritionalSatus");


        Entity qualification = schema.addEntity("Qualification");
        qualification.addIdProperty();
        qualification.addLongProperty("providerId");
        qualification.addLongProperty("qualificationValue");
        qualification.addStringProperty("commentary");
        qualification.addStringProperty("mainMenu");
        qualification.addStringProperty("garnish");
        qualification.addIntProperty("order");
        qualification.addIntProperty("statusSend");
        qualification.addLongProperty("createdAt");
        qualification.addStringProperty("SendAppAt");
        qualification.addStringProperty("user");
        qualification.addStringProperty("httpDetail");


        Entity drinks = schema.addEntity("Drinks");
        drinks.addIdProperty();
        drinks.addIntProperty("drinkId");
        drinks.addStringProperty("description");
        drinks.addIntProperty("currentStock");
        drinks.addIntProperty("priceUnit");
        drinks.addStringProperty("provider");


        Entity lunch = schema.addEntity("Lunch");
        lunch.addIdProperty();
        lunch.addIntProperty("principalMenuCode");
        lunch.addIntProperty("priceUnit");
        lunch.addStringProperty("mainMenuDescription");
        lunch.addIntProperty("providerId");
        lunch.addStringProperty("menuDate");
        lunch.addDoubleProperty("ratingMenu");
        lunch.addByteArrayProperty("imageMenu");


        Entity order = schema.addEntity("Order");
        order.addIdProperty();
        order.addStringProperty("orderType");
        order.addIntProperty("statusOrder");
        order.addLongProperty("lunchId");
        order.addIntProperty("drinkId");
        order.addIntProperty("garnishId");
        order.addLongProperty("createdAt");
        order.addStringProperty("SendAppAt");
        order.addLongProperty("providerId");
        order.addStringProperty("orderAmount");
        order.addLongProperty("ratingLunch");
        order.addStringProperty("user");
        order.addStringProperty("httpDetail");

        Entity providers = schema.addEntity("Provider");
        providers.addIdProperty();
        providers.addIntProperty("providerId");
        providers.addStringProperty("providerName");
        providers.addByteArrayProperty("providerImage");


        Entity garnish = schema.addEntity("Garnish");
        garnish.addIdProperty();
        garnish.addIntProperty("garnishId");
        garnish.addIntProperty("lunchId");
        garnish.addStringProperty("description");
        garnish.addIntProperty("unitPrice");


        Entity comments = schema.addEntity("Comments");
        comments.addIdProperty();
        comments.addIntProperty("providerId");
        comments.addStringProperty("userName");
        comments.addStringProperty("lunchPackageDescription");
        comments.addIntProperty("ratingValue");
        comments.addStringProperty("dateComment");
        comments.addStringProperty("commentDescription");

        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("lastName");
        user.addStringProperty("identifyCard");
        user.addStringProperty("currentAmount");
        user.addStringProperty("userName");
        user.addByteArrayProperty("imageProfile");

        Entity providerRating = schema.addEntity("ProviderRating");
        providerRating.addIdProperty();
        providerRating.addStringProperty("providerName");
        providerRating.addIntProperty("maxRating");
        providerRating.addStringProperty("providerRating");
        providerRating.addIntProperty("totalUserComments");
        providerRating.addByteArrayProperty("providerImage");
        providerRating.addIntProperty("fiveStar");
        providerRating.addIntProperty("fourStar");
        providerRating.addIntProperty("threeStar");
        providerRating.addIntProperty("twoStar");
        providerRating.addIntProperty("oneStar");





        new DaoGenerator().generateAll(schema, "../app/src/main/java");


    }


}
