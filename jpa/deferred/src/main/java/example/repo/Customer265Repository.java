package example.repo;

import example.model.Customer265;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer265Repository extends CrudRepository<Customer265, Long> {

	List<Customer265> findByLastName(String lastName);
}
