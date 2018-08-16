package example.repo;

import example.model.Customer828;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer828Repository extends CrudRepository<Customer828, Long> {

	List<Customer828> findByLastName(String lastName);
}
