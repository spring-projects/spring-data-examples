package com.example.data.mongodb;

import com.example.spi.mongodb.atlas.AtlasRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Christoph Strobl
 */
public interface MovieRepository extends CrudRepository<Movie, String>, AtlasRepository<Movie> {

}
