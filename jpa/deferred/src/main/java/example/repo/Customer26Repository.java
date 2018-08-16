package example.repo;

import example.model.Customer26;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer26Repository extends CrudRepository<Customer26, Long> {

	List<Customer26> findByLastName(String lastName);
}
