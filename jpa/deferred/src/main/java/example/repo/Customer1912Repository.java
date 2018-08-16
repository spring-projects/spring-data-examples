package example.repo;

import example.model.Customer1912;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1912Repository extends CrudRepository<Customer1912, Long> {

	List<Customer1912> findByLastName(String lastName);
}
