package example.repo;

import example.model.Customer1030;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1030Repository extends CrudRepository<Customer1030, Long> {

	List<Customer1030> findByLastName(String lastName);
}
