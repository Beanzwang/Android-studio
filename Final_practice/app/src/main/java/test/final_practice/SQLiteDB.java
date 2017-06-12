package test.final_practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbeanz on 11/06/2017.
 */

public class SQLiteDB {

    private SQLiteHelper mHelper;

    SQLiteDB(Context context) { mHelper = new SQLiteHelper(context); }


    public List getData() {
        List<Stock> list = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();
        String columns[] = { Entry.COL_TITLE, Entry.COL_PRICE, Entry.COL_TIMESTAMP };
        Cursor cursor = db.query(Entry.TABLE_NAME, columns, null, null, null, null, null);

        while(cursor.moveToNext()) {  // moving the cursor to get the result.
            String title = cursor.getString(0);
            int price = cursor.getInt(1);
            int timestamp = cursor.getInt(2);
            Stock stock = new Stock(title, price, timestamp);
            list.add(stock);
        }
        cursor.close();
        return list;
    }

    public List findByTitle(String title) {
        List<Stock> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String columns[] = { Entry.COL_PRICE, Entry.COL_TIMESTAMP };
        String selection = Entry.COL_TITLE + " = ?;";
        String selectionArgs[] = {title};  /* replace ? with title string */

        Cursor cursor = db.query(Entry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            int price = cursor.getInt(1);
            int timestamp = cursor.getInt(2);
            Stock stock = new Stock(title, price, timestamp);
            list.add(stock);
        }
        cursor.close();

        return list;
    }

    public List findByTime(int startTime, int endTime) {
        List<Stock> list = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String columns[] = { Entry.COL_TITLE, Entry.COL_PRICE, Entry.COL_TIMESTAMP };
        // TODO: between
        String selection = Entry.COL_TIMESTAMP + " >= " + Integer.toString(startTime) + " AND ";

        return list;
    }

    public long insert(Stock stock) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Entry.COL_TITLE, stock.getTitle());
        values.put(Entry.COL_PRICE, stock.getPrice());
        values.put(Entry.COL_TIMESTAMP, stock.getTimestamp());

        /* if fail, return -1 */
        return db.insert(Entry.TABLE_NAME, null, values);
    }

    public class Entry implements BaseColumns {
        // To prevent someone from accidentally instantiating the contract class,
        private static final String TABLE_NAME = "entry";
        private static final String COL_TITLE = "title";
        private static final String COL_PRICE = "price";
        private static final String COL_TIMESTAMP = "timestamp";
    }

    private class SQLiteHelper extends SQLiteOpenHelper {

        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "price.db";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                        Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Entry.COL_TITLE + " TEXT," +
                        Entry.COL_TIMESTAMP + " INTEGER," +
                        Entry.COL_PRICE + " REAL)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

        SQLiteHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

//        onCreate() is only run when the database file did not exist and was just created.
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("SQLite", "db onCreate");
            db.execSQL(SQL_DELETE_ENTRIES);
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
