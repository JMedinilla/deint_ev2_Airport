package
    /**
     * View
     */jvmedin.deint.airport.interfaces;

import java.util.List;

import jvmedin.deint.airport.pojos.Airport;

/**
 * Interface for the List and his presenter
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public interface PresenterAirport {
    void selectAll();

    void selectOne(int id);

    void insertAirport(Airport airport);

    void updateAirport(Airport airport);

    void deleteAirport(Airport airport);

    interface View {
        void selectAllResponse(List<Airport> airports);

        void selectOneResponse(Airport airport);

        void insertResponse(long result, String airportCode);

        void updateResponse(int result);

        void deleteResponse(int result);
    }
}
