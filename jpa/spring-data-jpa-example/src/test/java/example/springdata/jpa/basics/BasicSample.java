/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licenseimport static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.example.domain.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
ess or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.basics;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;

import example.springdata.jpa.simple.User;

/**
 * This unit tests shows plain usage of {@link SimpleJpaRepository}.
 * 
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
public class BasicSample {

	private CrudRepository<User, Long> userRepository;
	private EntityManager em;

	/**
	 * Sets up a {@link SimpleJpaRepository} instance.
	 */
	@Before
	public void setUp() {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa.sample.plain");
		em = factory.createEntityManager();

		userRepository = new SimpleJpaRepository<User, Long>(User.class, em);

		em.getTransaction().begin();
	}

	@After
	public void tearDown() {
		em.getTransaction().rollback();
	}

	/**
	 * Tests saving users. Don't mimic transactionality shown here. It seriously lacks resource cleanup in case of an
	 * exception. Simplification serves descriptivness.
	 */
	@Test
	public void savingUsers() {

		User user = new User();
		user.setUsername("username");

		user = userRepository.save(user);

		assertEquals(user, userRepository.findOne(user.getId()));
	}
}
