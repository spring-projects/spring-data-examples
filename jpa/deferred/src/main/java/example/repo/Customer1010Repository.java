package example.repo;

import example.model.Customer1010;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1010Repository extends CrudRepository<Customer1010, Long> {

	List<Customer1010> findByLastName(String lastName);
}
