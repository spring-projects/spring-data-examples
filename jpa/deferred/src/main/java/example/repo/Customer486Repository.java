package example.repo;

import example.model.Customer486;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer486Repository extends CrudRepository<Customer486, Long> {

	List<Customer486> findByLastName(String lastName);
}
