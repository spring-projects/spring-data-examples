package example.repo;

import example.model.Customer219;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer219Repository extends CrudRepository<Customer219, Long> {

	List<Customer219> findByLastName(String lastName);
}
