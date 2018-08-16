package example.repo;

import example.model.Customer1133;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1133Repository extends CrudRepository<Customer1133, Long> {

	List<Customer1133> findByLastName(String lastName);
}
