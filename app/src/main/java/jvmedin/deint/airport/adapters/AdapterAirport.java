package jvmedin.deint.airport.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jvmedin.deint.airport.R;
import jvmedin.deint.airport.pojos.Airport;

/**
 * Adapter for the airports list
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class AdapterAirport extends ArrayAdapter<Airport> {
    private Context context;

    public AdapterAirport(Context context) {
        super(context, R.layout.adapter_airport);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        AirportHolder airportHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_airport, parent, false);
            airportHolder = new AirportHolder();
            airportHolder.code = (TextView) view.findViewById(R.id.adapter_code);
            airportHolder.country = (TextView) view.findViewById(R.id.adapter_country);
            airportHolder.date = (TextView) view.findViewById(R.id.adapter_date);

            view.setTag(airportHolder);
        } else {
            airportHolder = (AirportHolder) view.getTag();
        }

        Airport airport = getItem(position);
        if (airport != null) {
            airportHolder.code.setText(airport.getCode());
            airportHolder.country.setText(airport.getCountry());
            airportHolder.date.setText(airport.getDate());
        }

        return view;
    }

    /**
     * Method that returns an Airport
     *
     * @param position Position in the list
     * @return The airport in the given position
     */
    @Nullable
    @Override
    public Airport getItem(int position) {
        return super.getItem(position);
    }

    /**
     * Method used in the list fragment to update the
     * current list in the adapter
     *
     * @param airports List with all the new airports
     */
    public void updateAdapter(List<Airport> airports) {
        clear();
        if (airports != null) {
            if (airports.size() > 0) {
                addAll(airports);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Holder class for the airport rows
     */
    private class AirportHolder {
        TextView code;
        TextView country;
        TextView date;
    }
}
