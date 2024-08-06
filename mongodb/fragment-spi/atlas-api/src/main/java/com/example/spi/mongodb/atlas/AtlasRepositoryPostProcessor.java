/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.spi.mongodb.atlas;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author Christoph Strobl
 */
class AtlasRepositoryPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof RepositoryFactoryBeanSupport rfbs) {

            Field field = ReflectionUtils.findField(RepositoryFactoryBeanSupport.class, "repositoryInterface");
            ReflectionUtils.makeAccessible(field);
            if (Arrays.stream(((Class<?>) ReflectionUtils.getField(field, rfbs)).getInterfaces())
                .anyMatch(iface -> {
                    return iface.equals(AtlasRepository.class);
                })) {
                rfbs.setExposeMetadata(true);
            }
        }
        return bean;
    }
}
