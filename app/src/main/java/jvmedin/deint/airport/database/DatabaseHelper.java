package jvmedin.deint.airport.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jvmedin.deint.airport.MyApplication;

/**
 * Class used to manage the database object used
 * to do operations in the Manager
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "airport.db";
    private static final int DATABASE_VERSION = 1;

    private static volatile DatabaseHelper instance;
    private static SQLiteDatabase database;

    /**
     * Method to get an instance of the class
     * It's synchronized so only one object can have the access to the database
     * @return Instance of the class
     */
    public static synchronized DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }

    private DatabaseHelper() {
        super(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(DatabaseContract.AirportTable.SQL_CREATE);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(DatabaseContract.AirportTable.SQL_DELETE);
            onCreate(sqLiteDatabase);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    /**
     * Method to open the database at the beginning of the application
     * @return Access to the database
     */
    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    /**
     * Method used to get access to the database with one
     * SQLiteDatabase object, given to the Manager
     * @return
     */
    public SQLiteDatabase openDatabase() {
        database = getWritableDatabase();
        return database;
    }

    /**
     * Method to close the given object to the Manager
     */
    public void closeDatabase() {
        database.close();
    }
}
