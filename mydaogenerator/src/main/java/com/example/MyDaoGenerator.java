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


        Entity users = schema.addEntity("Users");
        users.addIdProperty();
        users.addStringProperty("userName");
        users.addStringProperty("password");
        users.addBooleanProperty("userStatus");
        users.addStringProperty("identifyCardNumber");


        Entity drinks = schema.addEntity("Drinks");
        drinks.addIdProperty();
        drinks.addStringProperty("description");
        drinks.addIntProperty("priceUnit");
        drinks.addStringProperty("provider");


        Entity lunch = schema.addEntity("Lunch");
        lunch.addIdProperty();
        lunch.addIntProperty("priceUnit");
        lunch.addStringProperty("mainMenuDescription");
        lunch.addLongProperty("providerId");
        lunch.addDateProperty("menuDate");
        lunch.addLongProperty("ratingMenu");


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
        providers.addStringProperty("providerName");


        Entity garnish = schema.addEntity("Garnish");
        garnish.addIdProperty();
        garnish.addLongProperty("lunchId");
        garnish.addStringProperty("description");
        garnish.addIntProperty("unitPrice");


        Entity comments = schema.addEntity("Comments");
        comments.addIdProperty();
        comments.addIntProperty("providerId");
        comments.addStringProperty("userName");
        comments.addStringProperty("lunchPackageDescription");
        comments.addStringProperty("drinkDescription");
        comments.addIntProperty("ratingValue");
        comments.addStringProperty("dateComment");
        comments.addStringProperty("commentDescription");

        new DaoGenerator().generateAll(schema, "../app/src/main/java");


    }


}
