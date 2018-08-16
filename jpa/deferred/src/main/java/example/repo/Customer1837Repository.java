package example.repo;

import example.model.Customer1837;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1837Repository extends CrudRepository<Customer1837, Long> {

	List<Customer1837> findByLastName(String lastName);
}
