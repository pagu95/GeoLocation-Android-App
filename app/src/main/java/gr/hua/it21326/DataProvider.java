package gr.hua.it21326;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.nio.DoubleBuffer;

/**
 * Created by xrhstos on 5/2/2018.
 */

public class DataProvider extends ContentProvider {

    private static final String AUTH = "gr.hua.it21326.DataProvider";
    public static final Uri COORDINATES_URI = Uri.parse("content://" + AUTH + "/data");

    final static int COO =1;

    private final static UriMatcher uriMatcher;
    SQLiteDatabase db;
    DatabaseHelper dbhelp;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTH, DatabaseHelper.TABLE_NAME,COO);
    }


    @Override
    public boolean onCreate() {
        dbhelp = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1)
    {
        Cursor cursor;

        db =dbhelp.getReadableDatabase();
        cursor = db.query(dbhelp.TABLE_NAME,strings,s,strings1,null,null,s1 );

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
                db = dbhelp.getWritableDatabase();
                if(uriMatcher.match(uri)== COO)
                {
                 db.insert(dbhelp.TABLE_NAME,null,contentValues);
                }
                db.close();
                getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    /*@Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

           int rowsDeleted = 0;

            // Used to match uris with Content Providers
            switch (uriMatcher.match(uri)) {
                case COO:
                    rowsDeleted = db.delete(DatabaseHelper.TABLE_NAME, s, strings);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }

            // getContentResolver provides access to the content model
            // notifyChange notifies all observers that a row was updated
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsDeleted;

        }*/
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return dbhelp.deleteRow(Integer.parseInt(selectionArgs[0]));
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
