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
package example.springdata.mongodb.util;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.data.util.Version;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * {@link TestRule} verifying server tests are executed against match a given version. This one can be used as
 * {@link ClassRule} eg. in context depending tests run with {@link SpringJUnit4ClassRunner} when the context would fail
 * to start in case of invalid version, or as simple {@link Rule} on specific tests.
 * 
 * @author Christoph Strobl
 * @since 1.6
 */
public class MongoVersionRule implements TestRule {

	private String host = "localhost";
	private int port = 27017;

	private final Version minVersion;
	private final Version maxVersion;

	private Version currentVersion;

	public MongoVersionRule(Version min, Version max) {
		this.minVersion = min;
		this.maxVersion = max;
	}

	public static MongoVersionRule any() {
		return new MongoVersionRule(new Version(0, 0, 0), new Version(9999, 9999, 9999));
	}

	public static MongoVersionRule atLeast(Version minVersion) {
		return new MongoVersionRule(minVersion, new Version(9999, 9999, 9999));
	}

	public static MongoVersionRule atMost(Version maxVersion) {
		return new MongoVersionRule(new Version(0, 0, 0), maxVersion);
	}

	public MongoVersionRule withServerRunningAt(String host, int port) {
		this.host = host;
		this.port = port;

		return this;
	}

	@Override
	public Statement apply(final Statement base, Description description) {

		initCurrentVersion();
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				if (currentVersion != null) {
					if (currentVersion.isLessThan(minVersion) || currentVersion.isGreaterThan(maxVersion)) {
						throw new AssumptionViolatedException(String.format(
								"Expected mongodb server to be in range %s to %s but found %s", minVersion, maxVersion, currentVersion));
					}
				}
				base.evaluate();
			}
		};
	}

	private void initCurrentVersion() {

		if (currentVersion == null) {
			try {
				MongoClient client;
				client = new MongoClient(host, port);
				DB db = client.getDB("test");
				CommandResult result = db.command(new BasicDBObjectBuilder().add("buildInfo", 1).get());
				this.currentVersion = Version.parse(result.get("version").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
