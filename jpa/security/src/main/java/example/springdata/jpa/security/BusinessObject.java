/*
 * Copyright 2014 the original author or authors.
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
package example.springdata.jpa.security;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Thomas Darimont
 */
@Entity
public class BusinessObject {

	@Id @GeneratedValue Long id;
	String data;

	@ManyToOne User owner;

	String lastModifiedByUsername;

	Date lastModifiedDate;

	public BusinessObject() {}

	public BusinessObject(String data, User owner) {

		this.data = data;
		this.owner = owner;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getLastModifiedByUsername() {
		return lastModifiedByUsername;
	}

	public void setLastModifiedByUsername(String lastModifiedByUsername) {
		this.lastModifiedByUsername = lastModifiedByUsername;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BusinessObject that = (BusinessObject) o;

		if (data != null ? !data.equals(that.data) : that.data != null)
			return false;
		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (owner != null ? !owner.equals(that.owner) : that.owner != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (data != null ? data.hashCode() : 0);
		result = 31 * result + (owner != null ? owner.hashCode() : 0);
		return result;
	}
}
