package jvmedin.deint.airport.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Airport POJO class
 *
 * @author Javier Medinilla
 * @version 1.0
 */
public class Airport implements Parcelable {
    private int id;
    private String code;
    private String country;
    private String date;
    private String notes;

    /**
     * Constructor without ID (for insert)
     */
    public Airport(String code, String country, String date, String notes) {
        this.code = code;
        this.country = country;
        this.date = date;
        this.notes = notes;
    }

    /**
     * Constructor with ID (for update and select
     */
    public Airport(int id, String code, String country, String date, String notes) {
        this.id = id;
        this.code = code;
        this.country = country;
        this.date = date;
        this.notes = notes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(code);
        parcel.writeString(country);
        parcel.writeString(date);
        parcel.writeString(notes);
    }

    protected Airport(Parcel in) {
        id = in.readInt();
        code = in.readString();
        country = in.readString();
        date = in.readString();
        notes = in.readString();
    }

    public static final Creator<Airport> CREATOR = new Creator<Airport>() {
        @Override
        public Airport createFromParcel(Parcel in) {
            return new Airport(in);
        }

        @Override
        public Airport[] newArray(int size) {
            return new Airport[size];
        }
    };
}
