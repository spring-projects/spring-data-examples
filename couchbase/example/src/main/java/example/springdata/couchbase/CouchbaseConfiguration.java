package example.springdata.couchbase;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Collections;
import java.util.List;

/**
 * Couchbase Configuration to connect to Couchbase data store
 *
 * @author Chandana Kithalagama
 */
@SpringBootApplication
@Configuration
@EnableCouchbaseRepositories
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

	@Override
	protected List<String> getBootstrapHosts() {
		return Collections.singletonList("192.168.99.100");
	}

	@Override
	protected String getBucketName() {
		return "travel-sample";
	}

	@Override
	protected String getBucketPassword() {
		return "";
	}
}
