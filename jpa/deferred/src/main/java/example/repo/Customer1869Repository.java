package example.repo;

import example.model.Customer1869;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1869Repository extends CrudRepository<Customer1869, Long> {

	List<Customer1869> findByLastName(String lastName);
}
