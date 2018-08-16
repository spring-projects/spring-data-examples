package example.repo;

import example.model.Customer520;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer520Repository extends CrudRepository<Customer520, Long> {

	List<Customer520> findByLastName(String lastName);
}
