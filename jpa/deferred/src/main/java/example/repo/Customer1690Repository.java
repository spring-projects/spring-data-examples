package example.repo;

import example.model.Customer1690;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1690Repository extends CrudRepository<Customer1690, Long> {

	List<Customer1690> findByLastName(String lastName);
}
