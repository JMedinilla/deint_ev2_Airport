package jvmedin.deint.airport.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import jvmedin.deint.airport.pojos.Airport;

/**
 * Class with the database methods
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class ManageAirports {
    private static ManageAirports instance;

    public static ManageAirports getInstance() {
        if (instance == null) {
            instance = new ManageAirports();
        }
        return instance;
    }

    /**
     * Method that selects all the airports in the table
     *
     * @return List of airports
     */
    public List<Airport> selectAll() {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.AirportTable.TABLE_NAME,
                DatabaseContract.AirportTable.ALL_COLUMNS, null, null, null, null, null);

        ArrayList<Airport> list = new ArrayList<>();
        Airport airport;
        if (cursor.moveToFirst()) {
            do {
                airport = new Airport(
                        cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4)
                );
                list.add(airport);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return list;
    }

    /**
     * Method that selects one airport in the tablle
     *
     * @param id Given id of the airport to select
     * @return Airport with the given id
     */
    public Airport selectOne(int id) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.AirportTable.TABLE_NAME,
                DatabaseContract.AirportTable.ALL_COLUMNS, "_id = ?", new String[]{String.valueOf(id)},
                null, null, null);

        Airport airport = null;
        if (cursor.moveToFirst()) {
            airport = new Airport(
                    cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)
            );
        }

        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return airport;
    }

    /**
     * Method that inserts an airport in the database
     *
     * @param airport Airport to insert
     * @return Long number to tell the result of the operation
     */
    public long insert(Airport airport) {
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        values.put(DatabaseContract.AirportTable.COLUMN_CODE, airport.getCode());
        values.put(DatabaseContract.AirportTable.COLUMN_COUNTRY, airport.getCountry());
        values.put(DatabaseContract.AirportTable.COLUMN_DATE, airport.getDate());
        values.put(DatabaseContract.AirportTable.COLUMN_NOTES, airport.getNotes());
        long res = sqLiteDatabase.insert(DatabaseContract.AirportTable.TABLE_NAME, null, values);
        DatabaseHelper.getInstance().closeDatabase();
        return res;
    }

    /**
     * Method that updates an airport in the database
     *
     * @param airport Airport to update
     * @return Integer number to tell the result of the operation
     */
    public int update(Airport airport) {
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        values.put(DatabaseContract.AirportTable.COLUMN_CODE, airport.getCode());
        values.put(DatabaseContract.AirportTable.COLUMN_COUNTRY, airport.getCountry());
        values.put(DatabaseContract.AirportTable.COLUMN_DATE, airport.getDate());
        values.put(DatabaseContract.AirportTable.COLUMN_NOTES, airport.getNotes());
        int res = sqLiteDatabase.update(DatabaseContract.AirportTable.TABLE_NAME, values,
                "_id = ?", new String[]{String.valueOf(airport.getId())});
        DatabaseHelper.getInstance().closeDatabase();
        return res;
    }

    /**
     * Method that deletes an airport in the database
     *
     * @param id Id of the airport that has to be deleted
     * @return Integer number to tell the result of the operation
     */
    public int delete(int id) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        int res = sqLiteDatabase.delete(DatabaseContract.AirportTable.TABLE_NAME, "_id = ?",
                new String[]{String.valueOf(id)});
        DatabaseHelper.getInstance().closeDatabase();
        return res;
    }
}
