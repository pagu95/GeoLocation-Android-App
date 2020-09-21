package gr.hua.it21326;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xrhstos on 11/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db" ;
    public static final String TABLE_NAME = "data" ;
    public static final String COL_1 = "id" ;
    public static final String COL_2 = "userid" ;
    public static final String COL_3 = "longitude" ;
    public static final String COL_4 = "latitude" ;
    public static final String COL_5 = "dt" ;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(id integer primary key autoincrement ," +
                "userid varchar(10), longitude float , latitude float, dt varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String userid, String longitude, String latitude, String dt){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,userid);
        contentValues.put(COL_3,longitude);
        contentValues.put(COL_4,latitude);
        contentValues.put(COL_5,dt);
        long result = db.insert(TABLE_NAME,null,contentValues);
            if(result == -1)
                return false;
            else
                return true;
    }

    public boolean update (String userid, String longitude, String latitude, String dt){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_3,longitude);
        contentValues.put(COL_4,latitude);
        contentValues.put(COL_5,dt);
        db.update(TABLE_NAME, contentValues, "userid = ?" , new String[] { userid } );
        return true;
    }

    public Cursor getData(){

        SQLiteDatabase db =this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;

    }
    public List<String> getAlldt() {
        List<String> dtList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] {COL_1, COL_2, COL_3, COL_4, COL_5},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Adding Datetime to list
                dtList.add(cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dtList;
    }

    public List<String> findData(String u, String t) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> dataList = new ArrayList<>();
        String selection = COL_2 + "='" + u + "' OR " + COL_5 + "='" + t + "'";
        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_1, COL_2, COL_3, COL_4, COL_5},
                selection,null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                dataList.add("userid: " + cursor.getString(1));
                dataList.add("Longitude a: " + cursor.getString(2));
                dataList.add("Latitude: " + cursor.getString(3));
                dataList.add("timestamp: " + cursor.getString(4));
                dataList.add("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dataList;
    }
    public int deleteRow(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_NAME, COL_1 + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
        return res;
    }

}
