package example.repo;

import example.model.Customer1587;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1587Repository extends CrudRepository<Customer1587, Long> {

	List<Customer1587> findByLastName(String lastName);
}
