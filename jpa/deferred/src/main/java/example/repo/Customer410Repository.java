package example.repo;

import example.model.Customer410;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer410Repository extends CrudRepository<Customer410, Long> {

	List<Customer410> findByLastName(String lastName);
}
