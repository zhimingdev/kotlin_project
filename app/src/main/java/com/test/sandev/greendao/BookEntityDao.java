package com.test.sandev.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.test.sandev.entity.BookEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK_ENTITY".
*/
public class BookEntityDao extends AbstractDao<BookEntity, Long> {

    public static final String TABLENAME = "BOOK_ENTITY";

    /**
     * Properties of entity BookEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property BookId = new Property(0, Long.class, "bookId", true, "_id");
        public final static Property BookType = new Property(1, int.class, "bookType", false, "BOOK_TYPE");
        public final static Property BookSort = new Property(2, int.class, "bookSort", false, "BOOK_SORT");
        public final static Property Year = new Property(3, int.class, "year", false, "YEAR");
        public final static Property Month = new Property(4, int.class, "month", false, "MONTH");
        public final static Property Day = new Property(5, int.class, "day", false, "DAY");
        public final static Property Time = new Property(6, long.class, "time", false, "TIME");
        public final static Property BookNote = new Property(7, String.class, "bookNote", false, "BOOK_NOTE");
        public final static Property Amount = new Property(8, double.class, "amount", false, "AMOUNT");
    }


    public BookEntityDao(DaoConfig config) {
        super(config);
    }
    
    public BookEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: bookId
                "\"BOOK_TYPE\" INTEGER NOT NULL ," + // 1: bookType
                "\"BOOK_SORT\" INTEGER NOT NULL ," + // 2: bookSort
                "\"YEAR\" INTEGER NOT NULL ," + // 3: year
                "\"MONTH\" INTEGER NOT NULL ," + // 4: month
                "\"DAY\" INTEGER NOT NULL ," + // 5: day
                "\"TIME\" INTEGER NOT NULL ," + // 6: time
                "\"BOOK_NOTE\" TEXT," + // 7: bookNote
                "\"AMOUNT\" REAL NOT NULL );"); // 8: amount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BookEntity entity) {
        stmt.clearBindings();
 
        Long bookId = entity.getBookId();
        if (bookId != null) {
            stmt.bindLong(1, bookId);
        }
        stmt.bindLong(2, entity.getBookType());
        stmt.bindLong(3, entity.getBookSort());
        stmt.bindLong(4, entity.getYear());
        stmt.bindLong(5, entity.getMonth());
        stmt.bindLong(6, entity.getDay());
        stmt.bindLong(7, entity.getTime());
 
        String bookNote = entity.getBookNote();
        if (bookNote != null) {
            stmt.bindString(8, bookNote);
        }
        stmt.bindDouble(9, entity.getAmount());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BookEntity entity) {
        stmt.clearBindings();
 
        Long bookId = entity.getBookId();
        if (bookId != null) {
            stmt.bindLong(1, bookId);
        }
        stmt.bindLong(2, entity.getBookType());
        stmt.bindLong(3, entity.getBookSort());
        stmt.bindLong(4, entity.getYear());
        stmt.bindLong(5, entity.getMonth());
        stmt.bindLong(6, entity.getDay());
        stmt.bindLong(7, entity.getTime());
 
        String bookNote = entity.getBookNote();
        if (bookNote != null) {
            stmt.bindString(8, bookNote);
        }
        stmt.bindDouble(9, entity.getAmount());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public BookEntity readEntity(Cursor cursor, int offset) {
        BookEntity entity = new BookEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // bookId
            cursor.getInt(offset + 1), // bookType
            cursor.getInt(offset + 2), // bookSort
            cursor.getInt(offset + 3), // year
            cursor.getInt(offset + 4), // month
            cursor.getInt(offset + 5), // day
            cursor.getLong(offset + 6), // time
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // bookNote
            cursor.getDouble(offset + 8) // amount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BookEntity entity, int offset) {
        entity.setBookId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBookType(cursor.getInt(offset + 1));
        entity.setBookSort(cursor.getInt(offset + 2));
        entity.setYear(cursor.getInt(offset + 3));
        entity.setMonth(cursor.getInt(offset + 4));
        entity.setDay(cursor.getInt(offset + 5));
        entity.setTime(cursor.getLong(offset + 6));
        entity.setBookNote(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAmount(cursor.getDouble(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BookEntity entity, long rowId) {
        entity.setBookId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BookEntity entity) {
        if(entity != null) {
            return entity.getBookId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BookEntity entity) {
        return entity.getBookId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}