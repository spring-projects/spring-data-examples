package example.repo;

import example.model.Customer820;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer820Repository extends CrudRepository<Customer820, Long> {

	List<Customer820> findByLastName(String lastName);
}
