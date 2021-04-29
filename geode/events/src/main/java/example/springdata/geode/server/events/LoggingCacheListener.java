/*
 * Copyright 2020-2021 the original author or authors.
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
package example.springdata.geode.server.events;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @author Patrick Johnson
 */
@Component
@CommonsLog
public class LoggingCacheListener<K, V> extends CacheListenerAdapter<K, V> {


	@Override
	public void afterCreate(EntryEvent<K, V> event) {
		log.info("In region [" + event.getRegion().getName() + "] created key [" + event.getKey() + "] value ["
				+ event.getNewValue() + "]");
	}

	@Override
	public void afterDestroy(EntryEvent<K, V> event) {
		log.info("In region [" + event.getRegion().getName() + "] destroyed key [" + event.getKey() + "] ");
	}

	@Override
	public void afterUpdate(EntryEvent<K, V> event) {
		log.info("In region [" + event.getRegion().getName() + "] updated key [" + event.getNewValue() + "] [oldValue ["
				+ event.getOldValue() + "]] new value [" + event.getNewValue() + "]");
	}
}
