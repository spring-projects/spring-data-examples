package example.repo;

import example.model.Customer192;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer192Repository extends CrudRepository<Customer192, Long> {

	List<Customer192> findByLastName(String lastName);
}
