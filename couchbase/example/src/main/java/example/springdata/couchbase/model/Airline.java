package example.springdata.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Airline {

    /* Sample record from travel-sample of Couchbase
    "id": 10123,
    "type": "airline",
    "name": "Texas Wings",
    "iata": "TQ",
    "icao": "TXW",
    "callsign": "TXW",
    "country": "United States"
    */

    @Id
    private int id;

    @Field
    private String type;

    @Field
    private String name;

    @Field("iata")
    private String iataCode;

    @Field
    private String icao;

    @Field
    private String callsign;

    @Field
    private String country;

    public Airline() {}

    public Airline(int id, String type, String name, String iataCode, String icao, String callsign, String country) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.iataCode = iataCode;
        this.icao = icao;
        this.callsign = callsign;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public String getIcao() {
        return icao;
    }

    public String getCallsign() {
        return callsign;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airline airline = (Airline) o;

        if (id != airline.id) return false;
        if (!type.equals(airline.type)) return false;
        if (!name.equals(airline.name)) return false;
        if (!iataCode.equals(airline.iataCode)) return false;
        if (icao != null ? !icao.equals(airline.icao) : airline.icao != null) return false;
        if (callsign != null ? !callsign.equals(airline.callsign) : airline.callsign != null) return false;
        return country.equals(airline.country);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + iataCode.hashCode();
        result = 31 * result + (icao != null ? icao.hashCode() : 0);
        result = 31 * result + (callsign != null ? callsign.hashCode() : 0);
        result = 31 * result + country.hashCode();
        return result;
    }
}
