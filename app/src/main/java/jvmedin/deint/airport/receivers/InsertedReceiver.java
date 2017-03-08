package jvmedin.deint.airport.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import jvmedin.deint.airport.R;

/**
 * Receiver for the insert action
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class InsertedReceiver extends BroadcastReceiver {
    public static final String ACTION_INSERT = "jvmedin.deint.airport.INSERT";

    public InsertedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String code = intent.getStringExtra("insertedAirportCode");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.notificationTitle));
        builder.setContentText(context.getString(R.string.receiverInsert_message) + code);
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
