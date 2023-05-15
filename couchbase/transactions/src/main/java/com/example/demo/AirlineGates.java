/*
 * Copyright 2012-2022 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * @author Michael Reiche
 */
@Document
public class AirlineGates {
	@Id String id;
	@Version Long version;
	@QueryIndexed String name;
	String iata;
	Long gates;

	public AirlineGates(String id, String name, String iata, Long gates) {
		this.id = id;
		this.name = name;
		this.iata = iata;
		this.gates = gates;
	}

	public String getId() {
		return id;
	}

	public Long getGates() {
		return gates;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"id\":" + id);
		sb.append(", \"name\":" + name);
		sb.append(", \"iata\":" + iata);
		sb.append(", \"gates\":" + gates);
		sb.append("}");

		return sb.toString();
	}

}
