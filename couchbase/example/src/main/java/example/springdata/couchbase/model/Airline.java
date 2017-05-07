package example.springdata.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * A domain object representing an Airline
 *
 * @author Chandana Kithalagama
 */
@ToString
@Getter
@EqualsAndHashCode
@Document
public class Airline {

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
}
