package example.repo;

import example.model.Customer1294;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1294Repository extends CrudRepository<Customer1294, Long> {

	List<Customer1294> findByLastName(String lastName);
}
