package example.repo;

import example.model.Customer1560;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1560Repository extends CrudRepository<Customer1560, Long> {

	List<Customer1560> findByLastName(String lastName);
}
