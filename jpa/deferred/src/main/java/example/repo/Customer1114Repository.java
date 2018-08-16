package example.repo;

import example.model.Customer1114;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1114Repository extends CrudRepository<Customer1114, Long> {

	List<Customer1114> findByLastName(String lastName);
}
