package example.repo;

import example.model.Customer316;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer316Repository extends CrudRepository<Customer316, Long> {

	List<Customer316> findByLastName(String lastName);
}
