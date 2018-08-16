package example.repo;

import example.model.Customer1192;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1192Repository extends CrudRepository<Customer1192, Long> {

	List<Customer1192> findByLastName(String lastName);
}
