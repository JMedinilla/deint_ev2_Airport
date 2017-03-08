package jvmedin.deint.airport.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import jvmedin.deint.airport.R;

/**
 * Receiver for the update action
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class UpdatedReceiver extends BroadcastReceiver {
    public static final String ACTION_UPDATE = "jvmedin.deint.airport.UPDATE";

    public UpdatedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, R.string.receiverUpdateToast, Toast.LENGTH_SHORT).show();
    }
}
