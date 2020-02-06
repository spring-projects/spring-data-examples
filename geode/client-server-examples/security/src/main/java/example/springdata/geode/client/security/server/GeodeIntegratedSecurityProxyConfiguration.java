package example.springdata.geode.client.security.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;


@Configuration
@EnableSecurity(securityManagerClassName = "example.springdata.geode.client.security.server.SecurityManagerProxy")
@Profile({"default", "geode-security-manager-proxy-configuration"})
public class GeodeIntegratedSecurityProxyConfiguration {

}
