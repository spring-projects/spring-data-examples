package example.repo;

import example.model.Customer47;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer47Repository extends CrudRepository<Customer47, Long> {

	List<Customer47> findByLastName(String lastName);
}
