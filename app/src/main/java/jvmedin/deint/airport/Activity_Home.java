package jvmedin.deint.airport;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import jvmedin.deint.airport.fragments.forms.FormAirport;
import jvmedin.deint.airport.fragments.lists.ListAirport;
import jvmedin.deint.airport.pojos.Airport;
import jvmedin.deint.airport.receivers.InsertedReceiver;
import jvmedin.deint.airport.receivers.UpdatedReceiver;

/**
 * Main activity of the application
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class Activity_Home extends AppCompatActivity
        implements ListAirport.ListAirportInterface, FormAirport.FormAirportInterface {
    ListAirport listAirport;
    FormAirport formAirport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listAirport = new ListAirport();
        showList();
    }

    /**
     * Method to show the list at the beginning of the application
     */
    private void showList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_home, listAirport, "listFragment");
        transaction.commit();
    }

    /**
     * Callback Form method
     *
     * @param airport Airport created on the Form
     * @param update  True if the airport is to update, False if the airport is to insert
     */
    @Override
    public void fromFormToHome(Airport airport, boolean update) {
        listAirport.fromHomeToList(airport, update);
    }

    /**
     * Callback List method to open the Form after doing click on the FloatingActionButton
     *
     * @param airport Airport given by the List, it's null if the user is going to insert a
     *                new airport or a real airport in the database if the user is going
     *                to update it
     */
    @Override
    public void openFormFragment(Airport airport) {
        formAirport = new FormAirport();
        Bundle bundle = new Bundle();
        bundle.putParcelable("airportUpdate", airport);
        formAirport.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_home, formAirport, "formFragment");
        transaction.addToBackStack("formFragment");
        transaction.commit();
    }

    /**
     * Callback List method for the insert operation
     *
     * @param success     True if the airport was updated, False if not
     * @param airportCode Code of the new inserted airport for the notification
     */
    @Override
    public void insertResponse(boolean success, String airportCode) {
        if (success) {
            Toast.makeText(this, R.string.successInsert, Toast.LENGTH_SHORT).show();
            onBackPressed();

            Intent intent = new Intent(InsertedReceiver.ACTION_INSERT);
            intent.putExtra("insertedAirportCode", airportCode);
            sendBroadcast(intent);
        } else {
            Toast.makeText(this, R.string.errorInsert, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback List method for the update peration
     *
     * @param success True if the airport was updated, False if not
     */
    @Override
    public void updateResponse(boolean success) {
        if (success) {
            sendBroadcast(new Intent(UpdatedReceiver.ACTION_UPDATE));
            onBackPressed();
        } else {
            Toast.makeText(this, R.string.errorUpdate, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback method for the delete operation
     *
     * @param success True if the airport was deleted, False if not
     */
    @Override
    public void deleteResponse(boolean success) {
        if (success) {
            Toast.makeText(this, R.string.successDelete, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.errorDelete, Toast.LENGTH_SHORT).show();
        }
    }
}
