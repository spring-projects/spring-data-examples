package example.repo;

import example.model.Customer1328;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1328Repository extends CrudRepository<Customer1328, Long> {

	List<Customer1328> findByLastName(String lastName);
}
