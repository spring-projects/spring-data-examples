package example.repo;

import example.model.Customer1594;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1594Repository extends CrudRepository<Customer1594, Long> {

	List<Customer1594> findByLastName(String lastName);
}
