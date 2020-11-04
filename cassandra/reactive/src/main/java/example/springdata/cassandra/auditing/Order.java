/*
 * Copyright 2020 the original author or authors.
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
package example.springdata.cassandra.auditing;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.domain.Persistable;

/**
 * An entity to represent a Person.
 *
 * @author Mark Paluch
 */
@Data
@Table
@RequiredArgsConstructor
public class Order implements Persistable<String> {

	@Transient boolean isNew;

	@Id final String orderId;

	@CreatedBy String createdBy;

	@CreatedDate Instant createdDate;

	@LastModifiedBy String lastModifiedBy;

	@LastModifiedDate Instant lastModifiedDate;

	@Override
	public String getId() {
		return getOrderId();
	}

	@Override
	public boolean isNew() {
		return isNew;
	}
}
