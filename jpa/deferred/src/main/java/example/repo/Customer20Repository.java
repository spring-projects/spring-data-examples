package example.repo;

import example.model.Customer20;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer20Repository extends CrudRepository<Customer20, Long> {

	List<Customer20> findByLastName(String lastName);
}
