package example.repo;

import example.model.Customer1047;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1047Repository extends CrudRepository<Customer1047, Long> {

	List<Customer1047> findByLastName(String lastName);
}
