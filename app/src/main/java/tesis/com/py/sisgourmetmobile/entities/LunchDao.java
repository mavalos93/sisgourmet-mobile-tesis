package tesis.com.py.sisgourmetmobile.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import tesis.com.py.sisgourmetmobile.entities.Lunch;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LUNCH".
*/
public class LunchDao extends AbstractDao<Lunch, Long> {

    public static final String TABLENAME = "LUNCH";

    /**
     * Properties of entity Lunch.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PriceUnit = new Property(1, Integer.class, "priceUnit", false, "PRICE_UNIT");
        public final static Property MainMenuDescription = new Property(2, String.class, "mainMenuDescription", false, "MAIN_MENU_DESCRIPTION");
        public final static Property ProviderId = new Property(3, Long.class, "providerId", false, "PROVIDER_ID");
        public final static Property GarnishDescription = new Property(4, String.class, "garnishDescription", false, "GARNISH_DESCRIPTION");
        public final static Property MenuDate = new Property(5, java.util.Date.class, "menuDate", false, "MENU_DATE");
        public final static Property RaitingMenu = new Property(6, Long.class, "raitingMenu", false, "RAITING_MENU");
    };


    public LunchDao(DaoConfig config) {
        super(config);
    }
    
    public LunchDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LUNCH\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PRICE_UNIT\" INTEGER," + // 1: priceUnit
                "\"MAIN_MENU_DESCRIPTION\" TEXT," + // 2: mainMenuDescription
                "\"PROVIDER_ID\" INTEGER," + // 3: providerId
                "\"GARNISH_DESCRIPTION\" TEXT," + // 4: garnishDescription
                "\"MENU_DATE\" INTEGER," + // 5: menuDate
                "\"RAITING_MENU\" INTEGER);"); // 6: raitingMenu
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LUNCH\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Lunch entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer priceUnit = entity.getPriceUnit();
        if (priceUnit != null) {
            stmt.bindLong(2, priceUnit);
        }
 
        String mainMenuDescription = entity.getMainMenuDescription();
        if (mainMenuDescription != null) {
            stmt.bindString(3, mainMenuDescription);
        }
 
        Long providerId = entity.getProviderId();
        if (providerId != null) {
            stmt.bindLong(4, providerId);
        }
 
        String garnishDescription = entity.getGarnishDescription();
        if (garnishDescription != null) {
            stmt.bindString(5, garnishDescription);
        }
 
        java.util.Date menuDate = entity.getMenuDate();
        if (menuDate != null) {
            stmt.bindLong(6, menuDate.getTime());
        }
 
        Long raitingMenu = entity.getRaitingMenu();
        if (raitingMenu != null) {
            stmt.bindLong(7, raitingMenu);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Lunch readEntity(Cursor cursor, int offset) {
        Lunch entity = new Lunch( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // priceUnit
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mainMenuDescription
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // providerId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // garnishDescription
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // menuDate
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6) // raitingMenu
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Lunch entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPriceUnit(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setMainMenuDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setProviderId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setGarnishDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMenuDate(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setRaitingMenu(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Lunch entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Lunch entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}