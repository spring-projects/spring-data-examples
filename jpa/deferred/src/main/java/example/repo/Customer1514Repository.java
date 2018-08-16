package example.repo;

import example.model.Customer1514;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1514Repository extends CrudRepository<Customer1514, Long> {

	List<Customer1514> findByLastName(String lastName);
}
