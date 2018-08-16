package example.repo;

import example.model.Customer1122;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1122Repository extends CrudRepository<Customer1122, Long> {

	List<Customer1122> findByLastName(String lastName);
}
