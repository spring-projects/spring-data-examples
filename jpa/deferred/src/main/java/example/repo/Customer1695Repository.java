package example.repo;

import example.model.Customer1695;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1695Repository extends CrudRepository<Customer1695, Long> {

	List<Customer1695> findByLastName(String lastName);
}
