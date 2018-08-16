package example.repo;

import example.model.Customer625;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer625Repository extends CrudRepository<Customer625, Long> {

	List<Customer625> findByLastName(String lastName);
}
