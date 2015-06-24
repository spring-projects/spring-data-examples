/*
 * Copyright 2014-2015 the original author or authors.
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
package example.springdata.mongodb.util;

import java.net.UnknownHostException;

import org.junit.AssumptionViolatedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.springframework.data.util.Version;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * {@link TestRule} verifying server tests are executed against match a given version. This one can be used as
 * {@link ClassRule} e.g. in context depending tests run with {@link SpringJUnit4ClassRunner} when the context would
 * fail to start in case of invalid version, or as simple {@link Rule} on specific tests.
 * 
 * @author Christoph Strobl
 * @author Alexander Golonzovsky
 * @since 1.6
 */
public class RequiresMongoDB extends ExternalResource {

	private String host = "localhost";
	private int port = 27017;

	private final Version minVersion;
	private final Version maxVersion;

	private Version currentVersion;

	public RequiresMongoDB(Version min, Version max) {
		this.minVersion = min;
		this.maxVersion = max;
	}

	public static RequiresMongoDB anyVersion() {
		return new RequiresMongoDB(new Version(0, 0, 0), new Version(9999, 9999, 9999));
	}

	public static RequiresMongoDB atLeast(Version minVersion) {
		return new RequiresMongoDB(minVersion, new Version(9999, 9999, 9999));
	}

	public static RequiresMongoDB atMost(Version maxVersion) {
		return new RequiresMongoDB(new Version(0, 0, 0), maxVersion);
	}

	public RequiresMongoDB serverRunningAt(String host, int port) {
		this.host = host;
		this.port = port;

		return this;
	}

	@Override
	protected void before() throws Throwable {

		initCurrentVersion();

		if (currentVersion.isLessThan(minVersion) || currentVersion.isGreaterThan(maxVersion)) {
			throw new AssumptionViolatedException(String.format(
					"Expected mongodb server to be in range %s to %s but found %s", minVersion, maxVersion, currentVersion));
		}
	}

	private void initCurrentVersion() {

		if (currentVersion == null) {
			try {
				DB db = new MongoClient(host, port).getDB("test");
				CommandResult result = db.command(new BasicDBObjectBuilder().add("buildInfo", 1).get());
				this.currentVersion = Version.parse(result.get("version").toString());
			} catch (com.mongodb.MongoTimeoutException | UnknownHostException e) {
				throw new AssumptionViolatedException("Seems as mongodb server is not running.", e);
			}
		}
	}
}
