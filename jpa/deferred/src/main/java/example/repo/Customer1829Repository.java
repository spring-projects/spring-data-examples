package example.repo;

import example.model.Customer1829;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1829Repository extends CrudRepository<Customer1829, Long> {

	List<Customer1829> findByLastName(String lastName);
}
