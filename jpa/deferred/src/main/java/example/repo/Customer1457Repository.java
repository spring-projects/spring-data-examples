package example.repo;

import example.model.Customer1457;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1457Repository extends CrudRepository<Customer1457, Long> {

	List<Customer1457> findByLastName(String lastName);
}
