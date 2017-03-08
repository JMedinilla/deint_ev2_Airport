package jvmedin.deint.airport.database;

import android.provider.BaseColumns;

/**
 * Class with the final string names of the elements in the database
 *
 * @author Javier Medinilla
 * @version 1.0
 */
class DatabaseContract {
    /**
     * Airport table
     */
    static final class AirportTable implements BaseColumns {
        static final String TABLE_NAME = "airports";
        static final String COLUMN_ID = _ID;
        static final String COLUMN_CODE = "codes";
        static final String COLUMN_COUNTRY = "countries";
        static final String COLUMN_DATE = "dates";
        static final String COLUMN_NOTES = "notes";

        /**
         * Columns of the table
         */
        static final String[] ALL_COLUMNS = new String[]{
                COLUMN_ID, COLUMN_CODE, COLUMN_COUNTRY,
                COLUMN_DATE, COLUMN_NOTES
        };

        /**
         * SQL sentence to create the table
         */
        static final String SQL_CREATE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL,"
                        + " %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
                TABLE_NAME, COLUMN_ID, COLUMN_CODE, COLUMN_COUNTRY, COLUMN_DATE, COLUMN_NOTES
        );

        /**
         * SQL sentence to delete the table
         */
        static final String SQL_DELETE = String.format(
                "DROP TABLE IF EXISTS %s", TABLE_NAME
        );
    }
}
