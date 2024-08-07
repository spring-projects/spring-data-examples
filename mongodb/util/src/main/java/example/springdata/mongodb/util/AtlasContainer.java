/*
 * Copyright 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.mongodb.util;

import java.util.List;

import org.springframework.util.StringUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Christoph Strobl
 */
public class AtlasContainer extends GenericContainer<AtlasContainer> {

    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("mongodb/mongodb-atlas-local");
    private static final String DEFAULT_TAG = "latest";
    private static final String MONGODB_DATABASE_NAME_DEFAULT = "test";

    public AtlasContainer() {
        this(DEFAULT_IMAGE_NAME.withTag(DEFAULT_TAG));
    }

    public AtlasContainer(String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    public AtlasContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME);
        setExposedPorts(List.of(27017));
    }

    public String getConnectionString() {
        return getConnectionString(MONGODB_DATABASE_NAME_DEFAULT);
    }

    /**
     * Gets a connection string url.
     *
     * @return a connection url pointing to a mongodb instance
     */
    public String getConnectionString(String database) {
        return String.format("mongodb://%s:%d/%s?directConnection=true", getHost(), getMappedPort(27017), StringUtils.hasText(database) ? database : MONGODB_DATABASE_NAME_DEFAULT);
    }
}
