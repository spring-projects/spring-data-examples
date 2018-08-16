package example.repo;

import example.model.Customer770;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer770Repository extends CrudRepository<Customer770, Long> {

	List<Customer770> findByLastName(String lastName);
}
