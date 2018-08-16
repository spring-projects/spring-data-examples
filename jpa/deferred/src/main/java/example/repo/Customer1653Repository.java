package example.repo;

import example.model.Customer1653;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1653Repository extends CrudRepository<Customer1653, Long> {

	List<Customer1653> findByLastName(String lastName);
}
