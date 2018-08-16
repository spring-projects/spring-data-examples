package example.repo;

import example.model.Customer1506;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1506Repository extends CrudRepository<Customer1506, Long> {

	List<Customer1506> findByLastName(String lastName);
}
