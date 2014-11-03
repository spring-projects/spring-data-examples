package example.springdata.elasticsearch.model;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

import java.util.List;

import lombok.Data;
import lombok.experimental.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

/**
 * @author Artur Konczak
 */
@Data
@Builder
@Document(indexName = "conference-index", shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
public class Conference {

	@Id
	private String id;
	private String name;
	@Field(type = Date)
	private String date;
	@GeoPointField
	private String location;
	private List<String> keywords;

	//do not remove it
	public Conference() {

	}

	//do not remove it - work around for lombok generated constructor for all params
	public Conference(String id, String name, String date, String location, List<String> keywords) {

		this.id = id;
		this.name = name;
		this.date = date;
		this.location = location;
		this.keywords = keywords;
	}
}
