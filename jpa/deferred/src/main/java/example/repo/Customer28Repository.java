package example.repo;

import example.model.Customer28;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer28Repository extends CrudRepository<Customer28, Long> {

	List<Customer28> findByLastName(String lastName);
}
