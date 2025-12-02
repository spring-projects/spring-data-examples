package example.springdata.elasticsearch.talk;

import example.springdata.elasticsearch.speaker.Speaker;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Elasticsearch talk document.
 */
@Data
@Builder
@Document(indexName = "talks")
public class Talk {

    /**
     * Document id.
     */
    @Id
    private String id;

    /**
     * Talk title.
     */
    private String title;

    /**
     * Speakers of this talk.
     */
    @Field(type = FieldType.Nested)
    private List<Speaker> speakers;
}
