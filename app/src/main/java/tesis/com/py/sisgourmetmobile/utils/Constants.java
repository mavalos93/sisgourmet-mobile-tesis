package tesis.com.py.sisgourmetmobile.utils;

/**
 * Created by diego on 18/10/16.
 */

public class Constants {

    public static final String DEFAULT_DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_POSTGRES = "dd-MM-yyyy";

    public static final int RESPONSE_OK = 1;


    //Transaction Status
    public static final int TRANSACTION_SEND = 1;
    public static final int TRANSACTION_NO_SEND = 2;


    //Transaction Types
    public static final String MAIN_MENU_ORDER = "MENU PRINCIPAL";
    public static final String GARNISH_ORDER = "GUARNICION";
    public static final String LUNCH_PACKAGE_ORDER = "PAQUETE DE ALMUERZO";
    public static final String DRINK_ORDER = "BEBIDAS";

    // RESPONSES CODES
    public static final int AUTH_ERROR_CODE = 401;
    public static final int TOKEN_EXPIRED_CODE = 409;

    // ACTIONS & ERRORS
    public static final String NO_CONECTION_ERROR = "NO_CONECTION_ERROR";
    public static final String SERVER_ERROR = "SERVER_ERROR";
    public static final String ACTION_VIEW_DATA = "ACTION_VIEW_DATA";


    // ACTIONS APP
    public static final String GET_PROVIDER_ACTION = "GET_PROVIDER_ACTION";
    public static final String ACTION_SELECTED_MENU = "ACTION_SELECTED_MENU";
    public static final String SERIALIZABLE = "SERIALIZABLE";
    public static final String SINGLE_LUNCH_OBJECT = "SINGLE_LUNCH_OBJECT";
    public static final String SEND_ORDER_OBJECT = "SEND_ORDER_OBJECT";
    public static final String ACTION_QUALIFICATION_MENU = "ACTION_QUALIFICATION_MENU";


}
