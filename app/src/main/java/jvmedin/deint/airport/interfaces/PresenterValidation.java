package jvmedin.deint.airport.interfaces;

import jvmedin.deint.airport.pojos.Airport;

/**
 * Interface for the Form and his presenter
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public interface PresenterValidation {
    void validateAirport(Airport airport);

    interface View {
        void validateResponse(boolean result, Airport airport, String message);
    }
}
