package example.repo;

import example.model.Customer485;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer485Repository extends CrudRepository<Customer485, Long> {

	List<Customer485> findByLastName(String lastName);
}
