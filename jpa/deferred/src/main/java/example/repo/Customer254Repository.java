package example.repo;

import example.model.Customer254;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer254Repository extends CrudRepository<Customer254, Long> {

	List<Customer254> findByLastName(String lastName);
}
