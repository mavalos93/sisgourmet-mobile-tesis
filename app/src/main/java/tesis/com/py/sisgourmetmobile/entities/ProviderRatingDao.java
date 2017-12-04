package tesis.com.py.sisgourmetmobile.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import tesis.com.py.sisgourmetmobile.entities.ProviderRating;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PROVIDER_RATING".
*/
public class ProviderRatingDao extends AbstractDao<ProviderRating, Long> {

    public static final String TABLENAME = "PROVIDER_RATING";

    /**
     * Properties of entity ProviderRating.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ProviderId = new Property(1, Integer.class, "providerId", false, "PROVIDER_ID");
        public final static Property ProviderName = new Property(2, String.class, "providerName", false, "PROVIDER_NAME");
        public final static Property MaxRating = new Property(3, Integer.class, "maxRating", false, "MAX_RATING");
        public final static Property ProviderRating = new Property(4, String.class, "providerRating", false, "PROVIDER_RATING");
        public final static Property TotalUserComments = new Property(5, Integer.class, "totalUserComments", false, "TOTAL_USER_COMMENTS");
        public final static Property ProviderImage = new Property(6, byte[].class, "providerImage", false, "PROVIDER_IMAGE");
        public final static Property FiveStar = new Property(7, Integer.class, "fiveStar", false, "FIVE_STAR");
        public final static Property FourStar = new Property(8, Integer.class, "fourStar", false, "FOUR_STAR");
        public final static Property ThreeStar = new Property(9, Integer.class, "threeStar", false, "THREE_STAR");
        public final static Property TwoStar = new Property(10, Integer.class, "twoStar", false, "TWO_STAR");
        public final static Property OneStar = new Property(11, Integer.class, "oneStar", false, "ONE_STAR");
    };


    public ProviderRatingDao(DaoConfig config) {
        super(config);
    }
    
    public ProviderRatingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PROVIDER_RATING\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PROVIDER_ID\" INTEGER," + // 1: providerId
                "\"PROVIDER_NAME\" TEXT," + // 2: providerName
                "\"MAX_RATING\" INTEGER," + // 3: maxRating
                "\"PROVIDER_RATING\" TEXT," + // 4: providerRating
                "\"TOTAL_USER_COMMENTS\" INTEGER," + // 5: totalUserComments
                "\"PROVIDER_IMAGE\" BLOB," + // 6: providerImage
                "\"FIVE_STAR\" INTEGER," + // 7: fiveStar
                "\"FOUR_STAR\" INTEGER," + // 8: fourStar
                "\"THREE_STAR\" INTEGER," + // 9: threeStar
                "\"TWO_STAR\" INTEGER," + // 10: twoStar
                "\"ONE_STAR\" INTEGER);"); // 11: oneStar
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PROVIDER_RATING\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ProviderRating entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer providerId = entity.getProviderId();
        if (providerId != null) {
            stmt.bindLong(2, providerId);
        }
 
        String providerName = entity.getProviderName();
        if (providerName != null) {
            stmt.bindString(3, providerName);
        }
 
        Integer maxRating = entity.getMaxRating();
        if (maxRating != null) {
            stmt.bindLong(4, maxRating);
        }
 
        String providerRating = entity.getProviderRating();
        if (providerRating != null) {
            stmt.bindString(5, providerRating);
        }
 
        Integer totalUserComments = entity.getTotalUserComments();
        if (totalUserComments != null) {
            stmt.bindLong(6, totalUserComments);
        }
 
        byte[] providerImage = entity.getProviderImage();
        if (providerImage != null) {
            stmt.bindBlob(7, providerImage);
        }
 
        Integer fiveStar = entity.getFiveStar();
        if (fiveStar != null) {
            stmt.bindLong(8, fiveStar);
        }
 
        Integer fourStar = entity.getFourStar();
        if (fourStar != null) {
            stmt.bindLong(9, fourStar);
        }
 
        Integer threeStar = entity.getThreeStar();
        if (threeStar != null) {
            stmt.bindLong(10, threeStar);
        }
 
        Integer twoStar = entity.getTwoStar();
        if (twoStar != null) {
            stmt.bindLong(11, twoStar);
        }
 
        Integer oneStar = entity.getOneStar();
        if (oneStar != null) {
            stmt.bindLong(12, oneStar);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ProviderRating readEntity(Cursor cursor, int offset) {
        ProviderRating entity = new ProviderRating( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // providerId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // providerName
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // maxRating
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // providerRating
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // totalUserComments
            cursor.isNull(offset + 6) ? null : cursor.getBlob(offset + 6), // providerImage
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // fiveStar
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // fourStar
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // threeStar
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // twoStar
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11) // oneStar
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ProviderRating entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProviderId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setProviderName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMaxRating(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setProviderRating(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTotalUserComments(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setProviderImage(cursor.isNull(offset + 6) ? null : cursor.getBlob(offset + 6));
        entity.setFiveStar(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setFourStar(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setThreeStar(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setTwoStar(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setOneStar(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ProviderRating entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ProviderRating entity) {
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
