package jvmedin.deint.airport.presenters;

import android.os.AsyncTask;

import java.util.List;

import jvmedin.deint.airport.database.ManageAirports;
import jvmedin.deint.airport.interfaces.PresenterAirport;
import jvmedin.deint.airport.pojos.Airport;

/**
 * Presenter for the List fragment
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class PresenterAirportImpl implements PresenterAirport {
    private PresenterAirport.View view;

    public PresenterAirportImpl(PresenterAirport.View view) {
        this.view = view;
    }

    /**
     * Async method to select all airports
     */
    @Override
    public void selectAll() {
        new AsyncTask<Void, Void, List<Airport>>() {
            @Override
            protected List<Airport> doInBackground(Void... voids) {
                return ManageAirports.getInstance().selectAll();
            }

            @Override
            protected void onPostExecute(List<Airport> airports) {
                super.onPostExecute(airports);
                view.selectAllResponse(airports);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                view.selectAllResponse(null);
            }
        }.execute();
    }

    /**
     * Async method to select one airport
     *
     * @param id ID of the airport to select
     */
    @Override
    public void selectOne(final int id) {
        new AsyncTask<Void, Void, Airport>() {
            @Override
            protected Airport doInBackground(Void... voids) {
                return ManageAirports.getInstance().selectOne(id);
            }

            @Override
            protected void onPostExecute(Airport airport) {
                super.onPostExecute(airport);
                view.selectOneResponse(airport);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                view.selectOneResponse(null);
            }
        }.execute();
    }

    /**
     * Async method to insert one airport
     *
     * @param airport Airport to insert
     */
    @Override
    public void insertAirport(final Airport airport) {
        final String airportCode = airport.getCode();
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return ManageAirports.getInstance().insert(airport);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                view.insertResponse(aLong, airportCode);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                int res = -1;
                view.insertResponse(res, airportCode);
            }
        }.execute();
    }

    /**
     * Async method to update one airport
     *
     * @param airport Airport to update
     */
    @Override
    public void updateAirport(final Airport airport) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return ManageAirports.getInstance().update(airport);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.updateResponse(integer);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                int res = 0;
                view.updateResponse(res);
            }
        }.execute();
    }

    /**
     * Async method to delete one airport
     *
     * @param airport Airporty to delete
     */
    @Override
    public void deleteAirport(final Airport airport) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return ManageAirports.getInstance().delete(airport.getId());
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.deleteResponse(integer);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                int res = 0;
                view.deleteResponse(res);
            }
        }.execute();
    }
}
