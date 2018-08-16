package example.repo;

import example.model.Customer1350;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1350Repository extends CrudRepository<Customer1350, Long> {

	List<Customer1350> findByLastName(String lastName);
}
