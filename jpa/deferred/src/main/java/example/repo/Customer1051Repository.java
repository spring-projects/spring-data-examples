package example.repo;

import example.model.Customer1051;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1051Repository extends CrudRepository<Customer1051, Long> {

	List<Customer1051> findByLastName(String lastName);
}
