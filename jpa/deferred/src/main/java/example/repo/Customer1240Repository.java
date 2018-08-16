package example.repo;

import example.model.Customer1240;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1240Repository extends CrudRepository<Customer1240, Long> {

	List<Customer1240> findByLastName(String lastName);
}
