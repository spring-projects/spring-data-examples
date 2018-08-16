package example.repo;

import example.model.Customer973;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer973Repository extends CrudRepository<Customer973, Long> {

	List<Customer973> findByLastName(String lastName);
}
