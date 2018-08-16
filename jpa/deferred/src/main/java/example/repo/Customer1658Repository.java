package example.repo;

import example.model.Customer1658;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1658Repository extends CrudRepository<Customer1658, Long> {

	List<Customer1658> findByLastName(String lastName);
}
