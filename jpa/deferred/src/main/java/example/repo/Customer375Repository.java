package example.repo;

import example.model.Customer375;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer375Repository extends CrudRepository<Customer375, Long> {

	List<Customer375> findByLastName(String lastName);
}
