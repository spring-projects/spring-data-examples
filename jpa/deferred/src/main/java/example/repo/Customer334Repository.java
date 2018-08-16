package example.repo;

import example.model.Customer334;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer334Repository extends CrudRepository<Customer334, Long> {

	List<Customer334> findByLastName(String lastName);
}
