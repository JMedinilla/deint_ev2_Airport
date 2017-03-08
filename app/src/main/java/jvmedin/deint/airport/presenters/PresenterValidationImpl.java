package jvmedin.deint.airport.presenters;

import java.util.Objects;

import jvmedin.deint.airport.interfaces.PresenterValidation;
import jvmedin.deint.airport.pojos.Airport;

/**
 * Presenter of the Form fragment
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class PresenterValidationImpl implements PresenterValidation {
    private PresenterValidation.View view;

    public PresenterValidationImpl(PresenterValidation.View view) {
        this.view = view;
    }

    /**
     * Method that validates if the airport is valid for the database
     *
     * @param airport Airport to validate
     */
    @Override
    public void validateAirport(Airport airport) {
        String message = "";
        boolean result = false;
        if (Objects.equals(airport.getCode(), "")) {
            message += "CODE empty\n";
        } else if (airport.getCode().length() < 3) {
            message += "CODE too short\n";
        } else if (airport.getCode().length() > 3) {
            message += "CODE too long\n";
        }
        if (Objects.equals(airport.getCountry(), "")) {
            message += "COUNTRY empty\n";
        }
        if (Objects.equals(airport.getNotes(), "")) {
            message += "NOTES empty\n";
        }
        if (Objects.equals(airport.getDate(), "")) {
            message += "DATE empty\n";
        }
        if (Objects.equals(message, "")) {
            result = true;
        }

        view.validateResponse(result, airport, message);
    }
}
