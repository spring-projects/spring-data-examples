package example.repo;

import example.model.Customer1913;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1913Repository extends CrudRepository<Customer1913, Long> {

	List<Customer1913> findByLastName(String lastName);
}
