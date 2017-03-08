package jvmedin.deint.airport.fragments.lists;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import jvmedin.deint.airport.R;
import jvmedin.deint.airport.adapters.AdapterAirport;
import jvmedin.deint.airport.interfaces.PresenterAirport;
import jvmedin.deint.airport.pojos.Airport;
import jvmedin.deint.airport.presenters.PresenterAirportImpl;

/**
 * List fragment to show airports
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class ListAirport extends Fragment implements PresenterAirport.View {
    private ListAirportInterface callback;

    ListView listView;
    FloatingActionButton actionButton;

    PresenterAirportImpl presenterAirport;
    AdapterAirport adapterAirport;

    boolean forUpdate;

    /**
     * Interface with the callback methods for the activity
     */
    public interface ListAirportInterface {
        void openFormFragment(Airport airport);

        void insertResponse(boolean success, String airportCode);

        void updateResponse(boolean success);

        void deleteResponse(boolean success);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        forUpdate = false;

        presenterAirport = new PresenterAirportImpl(this);
        adapterAirport = new AdapterAirport(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.fragmentList_listView);
        actionButton = (FloatingActionButton) view.findViewById(R.id.fragmentList_actionButton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openFormFragment(null);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Airport airport = adapterAirport.getItem(i);
                if (airport != null) {
                    forUpdate = true;
                    presenterAirport.selectOne(airport.getId());
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapterAirport);
        listView.setDivider(null);
        registerForContextMenu(listView);

        presenterAirport.selectAll();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ListAirportInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Airport airport = adapterAirport.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.context_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.deleteDialog_title);
                builder.setMessage(R.string.deleteDialog_message);
                builder.setPositiveButton(R.string.deleteDialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenterAirport.deleteAirport(airport);
                    }
                });
                builder.setNegativeButton(R.string.deleteDialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Method used in the activity to recieve an airport from the Form
     *
     * @param airport Airport created in the form
     * @param update  True if update, False if insert
     */
    public void fromHomeToList(Airport airport, boolean update) {
        if (update) {
            presenterAirport.updateAirport(airport);
        } else {
            presenterAirport.insertAirport(airport);
        }
    }

    /**
     * Response method for the presenter
     *
     * @param airports Airport list given by the presenter
     */
    @Override
    public void selectAllResponse(List<Airport> airports) {
        adapterAirport.updateAdapter(airports);
    }

    /**
     * Response method for the presenter
     *
     * @param airport Airport given by the presenter
     */
    @Override
    public void selectOneResponse(Airport airport) {
        if (forUpdate) {
            callback.openFormFragment(airport);
            forUpdate = false;
        }
    }

    /**
     * Response method for the presenter
     *
     * @param result      Long that tells if the airport was inserted
     * @param airportCode Code of the new airport
     */
    @Override
    public void insertResponse(long result, String airportCode) {
        if (result != -1) {
            callback.insertResponse(true, airportCode);
        } else {
            callback.insertResponse(false, airportCode);
        }
    }

    /**
     * Response method for the presenter
     *
     * @param result Integer that tells if the airport was updated
     */
    @Override
    public void updateResponse(int result) {
        if (result != 0) {
            callback.updateResponse(true);
        } else {
            callback.updateResponse(false);
        }
    }

    /**
     * Response method for the presenter
     *
     * @param result Integer that tells if the airport was deleted
     */
    @Override
    public void deleteResponse(int result) {
        if (result != 0) {
            callback.deleteResponse(true);
            presenterAirport.selectAll();
        } else {
            callback.deleteResponse(false);
        }
    }
}
