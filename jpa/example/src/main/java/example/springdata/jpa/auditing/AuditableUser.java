/*
 * Copyright 2013-2021 the original author or authors.
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
package example.springdata.jpa.auditing;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * User domain class that uses auditing functionality of Spring Data that can either be aquired implementing
 * {@link Auditable} or extend {@link AbstractAuditable}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AuditableUser {

	private @Id @GeneratedValue Long id;
	private String username;

	private @CreatedDate LocalDateTime createdDate;
	private @LastModifiedDate LocalDateTime lastModifiedDate;

	private @ManyToOne @CreatedBy AuditableUser createdBy;
	private @ManyToOne @LastModifiedBy AuditableUser lastModifiedBy;
}
