package tesis.com.py.sisgourmetmobile.entities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import tesis.com.py.sisgourmetmobile.entities.Qualification;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QUALIFICATION".
*/
public class QualificationDao extends AbstractDao<Qualification, Long> {

    public static final String TABLENAME = "QUALIFICATION";

    /**
     * Properties of entity Qualification.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Provider = new Property(1, String.class, "provider", false, "PROVIDER");
        public final static Property Qualification = new Property(2, String.class, "qualification", false, "QUALIFICATION");
        public final static Property Commentary = new Property(3, String.class, "commentary", false, "COMMENTARY");
    };


    public QualificationDao(DaoConfig config) {
        super(config);
    }
    
    public QualificationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QUALIFICATION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PROVIDER\" TEXT," + // 1: provider
                "\"QUALIFICATION\" TEXT," + // 2: qualification
                "\"COMMENTARY\" TEXT);"); // 3: commentary
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QUALIFICATION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Qualification entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String provider = entity.getProvider();
        if (provider != null) {
            stmt.bindString(2, provider);
        }
 
        String qualification = entity.getQualification();
        if (qualification != null) {
            stmt.bindString(3, qualification);
        }
 
        String commentary = entity.getCommentary();
        if (commentary != null) {
            stmt.bindString(4, commentary);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Qualification readEntity(Cursor cursor, int offset) {
        Qualification entity = new Qualification( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // provider
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // qualification
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // commentary
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Qualification entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setProvider(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setQualification(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCommentary(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Qualification entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Qualification entity) {
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
