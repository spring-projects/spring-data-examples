package example.repo;

import example.model.Customer1886;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1886Repository extends CrudRepository<Customer1886, Long> {

	List<Customer1886> findByLastName(String lastName);
}
