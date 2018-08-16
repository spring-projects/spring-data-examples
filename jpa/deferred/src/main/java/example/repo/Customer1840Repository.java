package example.repo;

import example.model.Customer1840;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1840Repository extends CrudRepository<Customer1840, Long> {

	List<Customer1840> findByLastName(String lastName);
}
