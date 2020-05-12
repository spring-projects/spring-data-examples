package example.springdata.couchbase.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

/**
 *  @author Denis Rosa
 *  Configuration class to connnect with couchbase
 */
@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {


    @Override
    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    }

    @Override
    public String getUserName() {
        return "Administrator";
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getBucketName() {
        return "travel-sample";
    }



    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
