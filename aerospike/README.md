# Spring Data Aerospike Example Application

## Preparation

### Install Aerospike
Follow the instructions at [Aerospike](http://www.aerospike.com/docs/operations/install/)

## Quick Start

### Maven configuration

Add the Maven dependency:

```xml
<dependency>
<groupId>org.springframework.data</groupId>
	<artifactId>spring-data-aerospike</artifactId>
	<version>1.0.0.BUILD-SNAPSHOT</version>
</dependency>
```

If you'd rather like the latest snapshots of the upcoming major version, use our Maven snapshot repository and declare the appropriate dependency version.

```xml
	<dependencies>

		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-commons</artifactId>
			<version>${springdata.commons}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-aerospike</artifactId>
			<version>1.0.0.BUILD-SNAPSHOT</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-keyvalue</artifactId>
			<version>${springdata.keyvalue}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>4.1.7.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-tx</artifactId>
			<version>4.1.7.RELEASE</version>
		</dependency>

		<dependency>
			<groupId>com.aerospike</groupId>
			<artifactId>aerospike-client</artifactId>
			<version>${aerospike}</version>
		</dependency>

		<!-- Logging -->
		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.17</version>
		</dependency>
		<dependency>
			<groupId>joda-time</groupId>
			<artifactId>joda-time</artifactId>
			<version>2.7</version>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-assembly-plugin</artifactId>
				</plugin>
				<plugin>
					<groupId>org.codehaus.mojo</groupId>
					<artifactId>wagon-maven-plugin</artifactId>
				</plugin>
				<plugin>
					<groupId>org.apache.maven.plugins</groupId>
					<artifactId>maven-compiler-plugin</artifactId>
					<version>3.1</version>
					<configuration>
						<source>1.6</source>
						<target>1.6</target>
					</configuration>
				</plugin>

			</plugins>

		</pluginManagement>
		<resources>
			<resource>
				<directory>${project.basedir}/src/main/lua</directory>
				<includes>
					<include>**/*.lua</include>
				</includes>
			</resource>
		</resources>
	</build>

	<repositories>
		<repository>
			<id>spring-libs-snapshot</id>
			<url>https://repo.spring.io/libs-snapshot</url>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-plugins-release</id>
			<url>https://repo.spring.io/plugins-release</url>
		</pluginRepository>
		<pluginRepository>
			<id>jcenter</id>
			<url>https://dl.bintray.com/asciidoctor/maven</url>
		</pluginRepository>
	</pluginRepositories>

```

### AerospikeTemplate

AerospikeTemplate is the central support class for Aerospike database operations. It provides:

* Basic POJO mapping support to and from Bins
* Convenience methods to interact with the store (insert object, update objects) and Aerospike specific ones.
* Connection affinity callback
* Exception translation into Spring's [technology agnostic DAO exception hierarchy](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/dao.html#dao-exceptions).

#### Template Example
```java
public class AerospikeApp {

	private static final Logger LOG = LoggerFactory
			.getLogger(AerospikeApp.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String localhost = "127.0.0.1";
			AerospikeClient client = new AerospikeClient(null, localhost, 3000);
			AerospikeTemplate aerospikeTemplate = new AerospikeTemplate(client,	"test");
			aerospikeTemplate.delete(Person.class);
			aerospikeTemplate.createIndex(Person.class,"Person_firstName_index_xx", "name", IndexType.STRING);
			Person personSven01 = new Person("Sven-01", "ZName", 25);
			Person personSven02 = new Person("Sven-02", "QName", 21);
			Person personSven03 = new Person("Sven-03", "AName", 24);
			Person personSven04 = new Person("Sven-04", "WName", 25);

			

			aerospikeTemplate.insert(personSven01);
			aerospikeTemplate.insert(personSven02);
			aerospikeTemplate.insert(personSven03);
			aerospikeTemplate.insert(personSven04);

			Query query = new Query(
					Criteria.where("Person").is("WName", "name"));

			Iterable<Person> it = aerospikeTemplate.find(query, Person.class);
			int count = 0;
			Person firstPerson = null;
			for (Person person : it) {
				firstPerson = person;
				LOG.info(firstPerson.toString());
				System.out.println(firstPerson.toString());
				count++;
			}
		}
		catch (AerospikeException e) {
			e.printStackTrace();
		}

	}

}
```
### Spring Data repositories

To simplify the creation of data repositories Spring Data Aerospike provides a generic repository programming model. It will automatically create a repository proxy for you that adds implementations of finder methods you specify on an interface.  

For example, given a `Person` class with first and last name properties, a `PersonRepository` interface that can query for `Person` by last name and when the first name matches a like expression is shown below:

```java
	public interface PersonRepository extends AerospikeRepository<Person, String> {

		List<Person> findByName(String name);

		List<Person> findByNameStartsWith(String prefix);

}
```

The queries issued on execution will be derived from the method name. Extending `CrudRepository` causes CRUD methods being pulled into the interface so that you can easily save and find single entities and collections of them.

You can have Spring automatically create a proxy for the interface by using the following JavaConfig:

```java
@Configuration
@EnableAerospikeRepositories(basePackages = "example.springdata.aerospike")
public class TestRepositoryConfig {
	public @Bean(destroyMethod = "close") AerospikeClient aerospikeClient() {

		ClientPolicy policy = new ClientPolicy();
		policy.failIfNotConnected = true;

		return new AerospikeClient(policy, "127.0.0.1", 3000);
	}

	public @Bean AerospikeTemplate aerospikeTemplate() {
		return new AerospikeTemplate(aerospikeClient(), "test");
	}

}
```

This sets up a connection to a local Aerospike instance and enables the detection of Spring Data repositories (through `@EnableAerospikeRepositories`).

This will find the repository interface and register a proxy object in the container. You can use it as shown below:

```java
public class RepositoryExample {

	@Autowired
	protected PersonRepository repository;

	@Autowired
	AerospikeOperations aerospikeOperations;

	@Autowired
	AerospikeClient client;

	/**
	 * @param ctx
	 */
	public RepositoryExample(ApplicationContext ctx) {
		aerospikeOperations = ctx.getBean(AerospikeTemplate.class);
		repository = (PersonRepository) ctx.getBean("personRepository");
		client = ctx.getBean(AerospikeClient.class);
	}

	/**
	 * @param args
	 */
	protected void setUp() {

		repository.deleteAll();

		Person dave = new Person("Dave-01", "Matthews", 42);
		Person donny = new Person("Dave-02", "Macintire", 39);
		Person oliver = new Person("Oliver-01", "Matthews", 4);
		Person carter = new Person("Carter-01", "Beauford", 49);
		Person boyd = new Person("Boyd-01", "Tinsley", 45);
		Person stefan = new Person("Stefan-01", "Lessard", 34);
		Person leroi = new Person("Leroi-01", "Moore", 41);
		Person leroi2 = new Person("Leroi-02", "Moore", 25);
		Person alicia = new Person("Alicia-01", "Keys", 30);

		repository.createIndex(Person.class, "person_name_index_repository", "name",IndexType.STRING);

		List<Person> all = (List<Person>) repository.save(Arrays.asList(oliver,
				dave, donny, carter, boyd, stefan, leroi, leroi2, alicia));

	}

	/**
	 * @param args
	 */
	protected void cleanUp() {

		repository.deleteAll();

	}

	/**
	 * 
	 */
	protected void executeRepositoryCall() {
		List<Person> result = repository.findByName("Beauford");
		System.out.println("Results for exact match of 'Beauford'");
		for (Person person : result) {
			System.out.println(person.toString());
		}
		System.out.println("Results for name startting with letter 'M'");
		List<Person> resultPartial = repository.findByNameStartsWith("M");
		for (Person person : resultPartial) {
			System.out.println(person.toString());
		}
	}

	public static void main(String[] args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(TestRepositoryConfig.class);
		RepositoryExample repositoryExample = new RepositoryExample(ctx);
		repositoryExample.setUp();
		repositoryExample.executeRepositoryCall();
		repositoryExample.cleanUp();

	}

}
```

## Contributing to Spring Data

Here are some ways for you to get involved in the community:

* Get involved with the Spring community on Stackoverflow and help out on the [spring-data-Aerospike](http://stackoverflow.com/questions/tagged/spring-data-Aerospike) tag by responding to questions and joining the debate.
* Watch for upcoming articles on Spring by [subscribing](http://spring.io/blog) to spring.io.

## A more robust example is available on GitHub 
A Spring Boot Web Application example that uses Spring boot, Thymeleaf, Spring MVC, Tomcat and Aerospike is available on GitHub [spring-boot-web-aerospike-application](https://github.com/carosys/spring-boot-web-aerospike-application)