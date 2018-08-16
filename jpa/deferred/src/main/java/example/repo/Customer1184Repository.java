package example.repo;

import example.model.Customer1184;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1184Repository extends CrudRepository<Customer1184, Long> {

	List<Customer1184> findByLastName(String lastName);
}
