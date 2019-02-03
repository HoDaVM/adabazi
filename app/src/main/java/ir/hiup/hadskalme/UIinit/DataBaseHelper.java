package ir.hiup.hadskalme.UIinit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ir.hiup.hadskalme.BuildConfig;

/**
 * Created by Mahdi Asiyabi on 12/21/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;

    //private String DB_PATH = mycontext.getApplicationContext().getPackageName()+"/databases/";
    private static String DB_NAME = "words.sqlite";//the extension may be .sqlite or .db
    public SQLiteDatabase myDataBase;
    /*private String DB_PATH = "/data/data/"
                        + mycontext.getApplicationContext().getPackageName()
                        + "/databases/";*/

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mycontext = context;
        //String myPath = "";//"/data/data/" + BuildConfig.APPLICATION_ID + databasePath;
        databasePathFullPath = context.getFilesDir().getAbsolutePath() + databasePath;
        File dbfile = new File(databasePathFullPath);
        if (!dbfile.exists()) dbfile.mkdirs();
        databasePathFullPath += databaseName;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    String databasePath = "/databases/";
    String databaseName = "words.sqlite";
    public static String databasePathFullPath = "";

    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            File dbfile = new File(databasePathFullPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);


        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(databasePathFullPath);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        myDataBase = SQLiteDatabase.openDatabase(databasePathFullPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public String getWord(int POINT, int ID) throws SQLException {
        //Open the database
        String sql = "SELECT * FROM words WHERE points=\"" + POINT + "\" AND forcategory=\"" + ID + "\" ORDER BY RANDOM() LIMIT 0,1";
        Cursor c = myDataBase.rawQuery(sql, null);
        String word = "";
        while (c.moveToNext()) {
            word = c.getString(2);
            return word;
        }
        return word;

    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
