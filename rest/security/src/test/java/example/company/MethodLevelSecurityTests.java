package example.company;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Collection of test cases used to verify method-level security.
 *
 * @author Greg Turnquist
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, SecurityConfiguration.class })
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class MethodLevelSecurityTests {

	private String baseUrl;

	@Value("${local.server.port}")
	private int port;

	@Autowired
	ItemRepository itemRepository;

	@Before
	public void setUp() {

		this.baseUrl = "http://localhost:" + port;
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testMethodLevelSecurityForNoCreds() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");
		String creds = new String(Base64.encode(("greg:turnquist").getBytes()));
		headers.set("Authorization", "Basic " + creds);

		RestTemplate rest = new RestTemplate();
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(null, headers);

		System.out.println("============= GET " + baseUrl + "/items");

		System.out.println(SecurityContextHolder.getContext().getAuthentication());

		ResponseEntity<JsonNode> itemsResponse = rest.exchange(baseUrl + "/items", HttpMethod.GET, request, JsonNode.class);
		itemsResponse.getHeaders().entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.forEach(header -> System.out.println(header));
		assertThat(itemsResponse.getHeaders().get("Content-Type"), hasItems("application/hal+json"));
		assertThat(itemsResponse.getStatusCode(), equalTo(HttpStatus.OK));
		System.out.println();
		System.out.println(itemsResponse.getBody());

		try {
			itemRepository.findAll();
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}

		try {
			itemRepository.save(new Item("MacBook Pro"));
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}

		try {
			itemRepository.delete(1L);
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}
	}

	@Test
	public void testMethodLevelSecurityForUsers() {

		SecurityUtils.runAs("system", "system", "ROLE_USER");

		itemRepository.findAll();

		try {
			itemRepository.save(new Item("MacBook Pro"));
			fail("Expected a security error");
		} catch (AccessDeniedException e) {
			// expected
		}
		try {
			itemRepository.delete(1L);
			fail("Expected a security error");
		} catch (AccessDeniedException e) {
			// expected
		}
	}

	@Test
	public void testMethodLevelSecurityForAdmins() {

		SecurityUtils.runAs("system", "system", "ROLE_USER", "ROLE_ADMIN");

		itemRepository.findAll();
		itemRepository.save(new Item("MacBook Pro"));
		itemRepository.delete(1L);
	}
}
