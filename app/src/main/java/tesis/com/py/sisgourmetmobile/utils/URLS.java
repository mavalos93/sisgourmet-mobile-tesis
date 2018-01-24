package tesis.com.py.sisgourmetmobile.utils;

/**
 * Created by Manu0 on 23/7/2017.
 */

public class URLS {



    private static final String MAIN_URL = "http://192.168.1.151:8090/sisgourmetWS/webresources";

    public static final String QUALIFICATION_URL = MAIN_URL + "/entities.calificacioncomentario/qualification";
    public static final String ORDER_URL = MAIN_URL + "/entities.pedidosalmuerzo/order";
    public static final String LOGIN_URL = MAIN_URL + "/entities.gesusuario/login";
    public static final String ALL_COMMENTS_URL = MAIN_URL + "/entities.calificacioncomentario/providerdata";
    public static final String ALL_MENU_DATA_URL = MAIN_URL+ "/entities.gesmenusprincipal/syncAllMenus";
    public static final String CANCEL_ORDER_URL = MAIN_URL+ "/entities.pedidosalmuerzo/cancelOrder";


}
