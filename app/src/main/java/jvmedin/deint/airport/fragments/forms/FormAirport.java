package jvmedin.deint.airport.fragments.forms;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import jvmedin.deint.airport.R;
import jvmedin.deint.airport.interfaces.PresenterValidation;
import jvmedin.deint.airport.pojos.Airport;
import jvmedin.deint.airport.presenters.PresenterValidationImpl;

/**
 * Form fragment to insert and update an airport
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class FormAirport extends Fragment implements PresenterValidation.View {
    private FormAirportInterface callback;

    EditText edtCode;
    EditText edtCity;
    TextView txtDate;
    Button btnDate;
    EditText edtNotes;
    Button btnAdd;

    PresenterValidationImpl presenterValidation;

    Airport updateAirport;
    boolean forUpdate;

    /**
     * Interface with the callback methods for the activity
     */
    public interface FormAirportInterface {
        void fromFormToHome(Airport airport, boolean update);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        updateAirport = null;
        forUpdate = false;

        presenterValidation = new PresenterValidationImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        edtCode = (EditText) view.findViewById(R.id.edtCode);
        edtCity = (EditText) view.findViewById(R.id.edtCity);
        edtNotes = (EditText) view.findViewById(R.id.edtNotes);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        btnDate = (Button) view.findViewById(R.id.btnDate);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = java.util.Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        txtDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtCode.getText().toString();
                String country = edtCity.getText().toString();
                String notes = edtNotes.getText().toString();
                String date = txtDate.getText().toString();

                Airport airport = new Airport(code, country, date, notes);
                presenterValidation.validateAirport(airport);
            }
        });

        Bundle bundle = getArguments();
        Airport ap;
        if (bundle != null) {
            ap = bundle.getParcelable("airportUpdate");
            if (ap != null) {
                forUpdate = true;
                updateAirport = ap;

                edtCode.setText(updateAirport.getCode());
                edtCity.setText(updateAirport.getCountry());
                edtNotes.setText(updateAirport.getNotes());
                txtDate.setText(updateAirport.getDate());
            }
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (FormAirportInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    /**
     * Response method for the presenter
     *
     * @param result  True if valid, False if invalid
     * @param airport Airport validated
     * @param message Message to show if the airport is invalid
     */
    @Override
    public void validateResponse(boolean result, Airport airport, String message) {
        if (result) {
            if (forUpdate) {
                airport.setId(updateAirport.getId());
                callback.fromFormToHome(airport, true);
            } else {
                callback.fromFormToHome(airport, false);
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.alertInsert_title);
            builder.setMessage(message);
            builder.setPositiveButton(R.string.alertInsert_positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setIconAttribute(android.R.attr.alertDialogIcon);
            dialog.show();
        }
    }
}
