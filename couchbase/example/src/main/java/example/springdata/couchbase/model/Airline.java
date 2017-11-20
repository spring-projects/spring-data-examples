package example.springdata.couchbase.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * A domain object representing an Airline
 *
 * @author Chandana Kithalagama
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
