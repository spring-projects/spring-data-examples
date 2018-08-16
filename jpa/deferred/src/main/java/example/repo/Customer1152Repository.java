package example.repo;

import example.model.Customer1152;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1152Repository extends CrudRepository<Customer1152, Long> {

	List<Customer1152> findByLastName(String lastName);
}
