package example.repo;

import example.model.Customer1650;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1650Repository extends CrudRepository<Customer1650, Long> {

	List<Customer1650> findByLastName(String lastName);
}
