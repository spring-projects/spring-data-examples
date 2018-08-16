package example.repo;

import example.model.Customer61;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer61Repository extends CrudRepository<Customer61, Long> {

	List<Customer61> findByLastName(String lastName);
}
