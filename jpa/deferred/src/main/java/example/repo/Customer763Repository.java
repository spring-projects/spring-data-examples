package example.repo;

import example.model.Customer763;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer763Repository extends CrudRepository<Customer763, Long> {

	List<Customer763> findByLastName(String lastName);
}
