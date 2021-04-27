/*
 * Copyright 2019-2021 the original author or authors.
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
package example.springdata.redis;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;

/**
 * @author Christoph Strobl
 */
public final class SensorData {

	public static final String KEY = "my-stream";

	public static final StringRecord RECORD_1234_0 = SensorData.create("S-12", "18", "r2d2").withId(RecordId.of("1234-0"));
	public static final StringRecord RECORD_1234_1 = SensorData.create("S-13", "9", "c3o0").withId(RecordId.of("1234-1"));
	public static final StringRecord RECORD_1235_0 = SensorData.create("S-13", "18.2", "bb8").withId(RecordId.of("1235-0"));

	public static StringRecord create(String sensor, String temperature, String checksum) {
		return StreamRecords.string(sensorData(sensor, temperature, checksum)).withStreamKey(KEY);
	}

	private static Map<String, String> sensorData(String... values) {

		Map<String, String> data = new LinkedHashMap<>();
		data.put("sensor-id", values[0]);
		data.put("temperature", values[1]);
		if (values.length >= 3 && values[2] != null) {
			data.put("checksum", values[2]);
		}
		return data;
	}
}
