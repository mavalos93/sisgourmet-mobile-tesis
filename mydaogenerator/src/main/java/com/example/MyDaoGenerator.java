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


        Entity calificacion = schema.addEntity("Qualification");
        calificacion.addIdProperty();
        calificacion.addStringProperty("provider");
        calificacion.addStringProperty("qualification");
        calificacion.addStringProperty("commentary");


        Entity users = schema.addEntity("Users");
        users.addIdProperty();
        users.addStringProperty("userName");
        users.addStringProperty("password");
        users.addBooleanProperty("userStatus");
        users.addStringProperty("identifyCardNumber");


        Entity drinks = schema.addEntity("Drinks");
        drinks.addIdProperty();
        drinks.addStringProperty("description");
        drinks.addIntProperty("currentStock");
        drinks.addIntProperty("minimunStock");
        drinks.addIntProperty("priceUnit");
        drinks.addStringProperty("provider");


        Entity lunch = schema.addEntity("Lunch");
        lunch.addIdProperty();
        lunch.addIntProperty("priceUnit");
        lunch.addStringProperty("mainMenuDescription");
        lunch.addLongProperty("providerId");
        lunch.addStringProperty("garnishDescription");
        lunch.addDateProperty("menuDate");
        lunch.addLongProperty("raitingMenu");



        Entity order = schema.addEntity("Order");
        order.addIdProperty();
        order.addStringProperty("orderType");
        order.addStringProperty("statusDescription");
        order.addIntProperty("statusOrder");
        order.addIntProperty("drinksId");
        order.addIntProperty("lunchId");
        order.addIntProperty("drinkDetails");
        order.addIntProperty("launchDetails");
        order.addDateProperty("createdAt");
        order.addDoubleProperty("orderAmount");

        Entity providers = schema.addEntity("Provider");
        providers.addIdProperty();
        providers.addStringProperty("providerName");
        providers.addStringProperty("providerType");


        new DaoGenerator().generateAll(schema, "../app/src/main/java");


    }


}
