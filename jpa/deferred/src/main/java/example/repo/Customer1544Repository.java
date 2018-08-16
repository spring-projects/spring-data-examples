package example.repo;

import example.model.Customer1544;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1544Repository extends CrudRepository<Customer1544, Long> {

	List<Customer1544> findByLastName(String lastName);
}
