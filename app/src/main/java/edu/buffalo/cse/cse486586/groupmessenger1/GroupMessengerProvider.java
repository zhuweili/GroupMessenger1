package edu.buffalo.cse.cse486586.groupmessenger1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * GroupMessengerProvider is a key-value table. Once again, please note that we do not implement
 * full support for SQL as a usual ContentProvider does. We re-purpose ContentProvider's interface
 * to use it as a key-value table.
 * 
 * Please read:
 * 
 * http://developer.android.com/guide/topics/providers/content-providers.html
 * http://developer.android.com/reference/android/content/ContentProvider.html
 * 
 * before you start to get yourself familiarized with ContentProvider.
 * 
 * There are two methods you need to implement---insert() and query(). Others are optional and
 * will not be tested.
 * 
 * @author stevko
 *
 */
public class GroupMessengerProvider extends ContentProvider {

    public static final String PREFS_NAME = "PA2";
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // You do not need to implement this.
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // You do not need to implement this.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /*
         * TODO: You need to implement this method. Note that values will have two columns (a key
         * column and a value column) and one row that contains the actual (key, value) pair to be
         * inserted.
         * 
         * For actual storage, you can use any option. If you know how to use SQL, then you can use
         * SQLite. But this is not a requirement. You can use other storage options, such as the
         * internal storage option that we used in PA1. If you want to use that option, please
         * take a look at the code for PA1.
         */

        //SharedPreferences.Editor edit2;

        sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);
        edit=sp.edit();
        //edit2=sp.edit();
        String key=(String)values.get("key");
        String value=(String)values.get("value");
        String into_avd=key+"-&&&-"+value;
        //edit.clear();
        //sp.edit().putString("key","1").putString("value", "2").commit();
        //sp.edit().putString("key2","3").putString("value2", "4").commit();
        System.out.println(key);
        System.out.println(value);

        edit.putString(key, into_avd);
        edit.commit();


        Log.v("insert", values.toString());
        return uri;
    }

    @Override
    public boolean onCreate() {
        // If you need to perform any one-time initialization task, please do it here.
        return false;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // You do not need to implement this.
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        /*
         * TODO: You need to implement this method. Note that you need to return a Cursor object
         * with the right format. If the formatting is not correct, then it is not going to work.
         *
         * If you use SQLite, whatever is returned from SQLite is a Cursor object. However, you
         * still need to be careful because the formatting might still be incorrect.
         *
         * If you use a file storage option, then it is your job to build a Cursor * object. I
         * recommend building a MatrixCursor described at:
         * http://developer.android.com/reference/android/database/MatrixCursor.html
         */

        sp=this.getContext().getSharedPreferences(PREFS_NAME, 0);
        String out_avd=sp.getString(selection, null);
        //out_avd=sp.getStringSet(selection, null);

        String[] got = out_avd.split("-&&&-");

        String key=got[0];
        String value=got[1];




        String[] row_name={"key", "value"};
        MatrixCursor mc=new MatrixCursor(row_name);
        MatrixCursor.RowBuilder add1=mc.newRow();
        add1.add(key);
        add1.add(value);

        Log.v("query", selection);
        return mc;



        //return null;
    }
}
