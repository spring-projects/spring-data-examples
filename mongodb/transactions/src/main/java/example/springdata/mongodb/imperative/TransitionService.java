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
package example.springdata.mongodb.imperative;

import example.springdata.mongodb.Process;
import example.springdata.mongodb.State;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Christoph Strobl
 * @currentRead The Core - Peter V. Brett
 */
@Service
@RequiredArgsConstructor
class TransitionService {

	final ProcessRepository repository;
	final MongoTemplate template;

	final AtomicInteger counter = new AtomicInteger(0);

	public Process newProcess() {
		return repository.save(new Process(counter.incrementAndGet(), State.CREATED, 0));
	}

	@Transactional
	public void run(Integer id) {

		var process = lookup(id);

		if (!State.CREATED.equals(process.state())) {
			return;
		}

		start(process);
		verify(process);
		finish(process);
	}

	private void finish(Process process) {

		template.update(Process.class).matching(Query.query(Criteria.where("id").is(process.id())))
				.apply(Update.update("state", State.DONE).inc("transitionCount", 1)).first();
	}

	void start(Process process) {

		template.update(Process.class).matching(Query.query(Criteria.where("id").is(process.id())))
				.apply(Update.update("state", State.ACTIVE).inc("transitionCount", 1)).first();
	}

	Process lookup(Integer id) {
		return repository.findById(id).get();
	}

	void verify(Process process) {
		Assert.state(process.id() % 3 != 0, "We're sorry but we needed to drop that one");
	}
}
