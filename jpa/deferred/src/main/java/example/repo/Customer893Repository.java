package example.repo;

import example.model.Customer893;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer893Repository extends CrudRepository<Customer893, Long> {

	List<Customer893> findByLastName(String lastName);
}
