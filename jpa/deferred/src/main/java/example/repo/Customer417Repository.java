package example.repo;

import example.model.Customer417;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer417Repository extends CrudRepository<Customer417, Long> {

	List<Customer417> findByLastName(String lastName);
}
