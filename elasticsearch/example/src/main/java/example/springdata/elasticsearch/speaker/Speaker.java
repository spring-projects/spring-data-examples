package example.springdata.elasticsearch.speaker;

import lombok.Builder;
import lombok.Data;

/**
 * Speaker of a talk.
 */
@Data
@Builder
public class Speaker {

    /**
     * Speaker name.
     */
    private String name;

    /**
     * Speaker position.
     */
    private String position;
}

