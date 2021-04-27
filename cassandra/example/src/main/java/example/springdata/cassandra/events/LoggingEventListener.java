/*
 * Copyright 2018-2021 the original author or authors.
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
package example.springdata.cassandra.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener;
import org.springframework.data.cassandra.core.mapping.event.AfterConvertEvent;
import org.springframework.data.cassandra.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.cassandra.core.mapping.event.AfterLoadEvent;
import org.springframework.data.cassandra.core.mapping.event.AfterSaveEvent;
import org.springframework.data.cassandra.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.cassandra.core.mapping.event.BeforeSaveEvent;

/**
 * {@link ApplicationListener} for Cassandra mapping events logging the events.
 *
 * @author Mark Paluch
 */
public class LoggingEventListener extends AbstractCassandraEventListener<Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingEventListener.class);

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener#onBeforeSave(org.springframework.data.cassandra.core.mapping.event.BeforeSaveEvent)
	 */
	@Override
	public void onBeforeSave(BeforeSaveEvent<Object> event) {
		LOGGER.info("onBeforeSave: {}, {}", event.getSource(), event.getStatement());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener#onAfterSave(org.springframework.data.cassandra.core.mapping.event.AfterSaveEvent)
	 */
	@Override
	public void onAfterSave(AfterSaveEvent<Object> event) {
		LOGGER.info("onAfterSave: {}", event.getSource());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener#onBeforeDelete(org.springframework.data.cassandra.core.mapping.event.BeforeDeleteEvent)
	 */
	@Override
	public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
		LOGGER.info("onBeforeDelete: {}", event.getSource());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener#onAfterDelete(org.springframework.data.cassandra.core.mapping.event.AfterDeleteEvent)
	 */
	@Override
	public void onAfterDelete(AfterDeleteEvent<Object> event) {
		LOGGER.info("onAfterDelete: {}", event.getSource());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener#onAfterLoad(org.springframework.data.cassandra.core.mapping.event.AfterLoadEvent)
	 */
	@Override
	public void onAfterLoad(AfterLoadEvent<Object> event) {
		LOGGER.info("onAfterLoad: {}", event.getSource());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.cassandra.core.mapping.event.AbstractCassandraEventListener#onAfterConvert(org.springframework.data.cassandra.core.mapping.event.AfterConvertEvent)
	 */
	@Override
	public void onAfterConvert(AfterConvertEvent<Object> event) {
		LOGGER.info("onAfterConvert: {}", event.getSource());
	}
}
