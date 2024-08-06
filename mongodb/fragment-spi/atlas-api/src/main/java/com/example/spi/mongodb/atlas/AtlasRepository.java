package com.example.spi.mongodb.atlas;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Limit;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.stereotype.Component;

/**
 * @author Christoph Strobl
 */
public interface AtlasRepository<T> {

    List<T> vectorSearch(String index, String path, List<Double> vector, Limit limit);
}
