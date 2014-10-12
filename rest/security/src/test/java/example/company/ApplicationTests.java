package example.company;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Some test cases demonstrating various aspects of the app.
 *
 * @author Greg Turnquist
 */
public class ApplicationTests {

	private AnnotationConfigEmbeddedWebApplicationContext context;
	private String baseUrl;

	@Before
	public void setUp() {

		this.context = new AnnotationConfigEmbeddedWebApplicationContext();
		this.context.register(TestConfiguration.class);
		this.context.refresh();

		this.baseUrl = "http://localhost:"
				+ this.context.getEmbeddedServletContainer().getPort();

		SecurityContextHolder.clearContext();
	}

	@Test
	public void testUrlSecurityForNoCreds() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");

		HttpEntity<MultiValueMap<String, Object>> baseRequest = new HttpEntity<>(headers);

		RestTemplate rest = new RestTemplate();

		System.out.println("============= GET " + baseUrl);

		ResponseEntity<JsonNode> baseResponse = rest.exchange(baseUrl, HttpMethod.GET, baseRequest, JsonNode.class);
		baseResponse.getHeaders().entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.forEach(header -> System.out.println(header));
		assertEquals("application/hal+json", baseResponse.getHeaders().get("Content-Type").get(0));
		assertEquals(HttpStatus.OK, baseResponse.getStatusCode());
		System.out.println();
		System.out.println(baseResponse.getBody());

		System.out.println("============= POST " + baseUrl + "/employees");

		HttpEntity<String> newEmployee = new HttpEntity<>("{firstName: Saruman, lastName: the White, title: Wizard}",
				headers);

		try {
			rest.exchange(baseUrl + "/employees", HttpMethod.POST, newEmployee, JsonNode.class);
			fail("Expected a security error");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
		}
	}

	@Test
	public void testUrlSecurityForUsers() {

		System.out.println("============= GET " + baseUrl + "/employees");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");

		String userCreds = new String(Base64.encode(("greg:turnquist").getBytes()));
		headers.set("Authorization", "Basic " + userCreds);
		headers.set("Accept", "application/hal+json");

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(headers);

		RestTemplate rest = new RestTemplate();

		ResponseEntity<JsonNode> employeesResponse = rest.exchange(baseUrl + "/employees", HttpMethod.GET, request, JsonNode.class);
		employeesResponse.getHeaders().entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.forEach(header -> System.out.println(header));
		assertEquals("application/hal+json", employeesResponse.getHeaders().get("Content-Type").get(0));
		assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());
		System.out.println();
		System.out.println(employeesResponse.getBody());

		System.out.println("============= POST " + baseUrl + "/employees");

		HttpEntity<String> newEmployee = new HttpEntity<>(
				"{\"firstName\": \"Saruman\", " +
				"\"lastName\": \"the White\", " +
				"\"title\": \"Wizard\"}",
				headers);

		try {
			rest.exchange(baseUrl + "/employees", HttpMethod.POST, newEmployee, JsonNode.class);
			fail("Expected a security error");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.FORBIDDEN, e.getStatusCode());
		}
	}

	@Test
	public void testUrlSecurityForAdmins() {

		System.out.println("============= GET " + baseUrl + "/employees");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/hal+json");

		String userCreds = new String(Base64.encode(("ollie:gierke").getBytes()));
		headers.set("Authorization", "Basic " + userCreds);
		headers.set("Accept", "application/hal+json");

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(headers);

		RestTemplate rest = new RestTemplate();

		ResponseEntity<JsonNode> employeesResponse = rest.exchange(baseUrl + "/employees", HttpMethod.GET, request, JsonNode.class);
		employeesResponse.getHeaders().entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.forEach(header -> System.out.println(header));
		assertEquals("application/hal+json", employeesResponse.getHeaders().get("Content-Type").get(0));
		assertEquals(HttpStatus.OK, employeesResponse.getStatusCode());
		System.out.println();
		System.out.println(employeesResponse.getBody());

		System.out.println("============= POST " + baseUrl + "/employees");

		headers.add("Content-Type", "application/json");

		HttpEntity<String> newEmployee = new HttpEntity<>(
				"{\"firstName\": \"Saruman\", " +
				"\"lastName\": \"the White\", " +
				"\"title\": \"Wizard\"}",
				headers);

		ResponseEntity<JsonNode> response = rest.exchange(baseUrl + "/employees", HttpMethod.POST, newEmployee, JsonNode.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		String location = response.getHeaders().get("Location").get(0);
		Employee employee = rest.getForObject(location, Employee.class);
		assertEquals("Saruman", employee.getFirstName());
		assertEquals("the White", employee.getLastName());
		assertEquals("Wizard", employee.getTitle());
		System.out.println(rest.getForObject(location, String.class));
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
		assertEquals("application/hal+json", itemsResponse.getHeaders().get("Content-Type").get(0));
		assertEquals(HttpStatus.OK, itemsResponse.getStatusCode());
		System.out.println();
		System.out.println(itemsResponse.getBody());

		ItemRepository itemRepository = this.context.getBean(ItemRepository.class);

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

		ItemRepository itemRepository = this.context.getBean(ItemRepository.class);

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("system", "system",
						AuthorityUtils.createAuthorityList("ROLE_USER")));

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

		ItemRepository itemRepository = this.context.getBean(ItemRepository.class);

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("system", "system",
						AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN")));

		itemRepository.findAll();
		itemRepository.save(new Item("MacBook Pro"));
		itemRepository.delete(1L);
	}

	/**
	 * This configuration is used to have embedded Tomcat spun up on a randomly picked, free port.
	 */
	@Configuration
	@Import({Application.class, SecurityConfiguration.class})
	protected static class TestConfiguration {

		@Bean
		TomcatEmbeddedServletContainerFactory containerFactory() {
			return new TomcatEmbeddedServletContainerFactory(0);
		}

	}

}
