package jvmedin.deint.airport;

import android.app.Application;
import android.content.Context;

import jvmedin.deint.airport.database.DatabaseHelper;

/**
 * Application class
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        DatabaseHelper.getInstance().open();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
