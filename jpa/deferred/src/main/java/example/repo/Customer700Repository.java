package example.repo;

import example.model.Customer700;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer700Repository extends CrudRepository<Customer700, Long> {

	List<Customer700> findByLastName(String lastName);
}
