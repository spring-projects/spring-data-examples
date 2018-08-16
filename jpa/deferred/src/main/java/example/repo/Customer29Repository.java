package example.repo;

import example.model.Customer29;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer29Repository extends CrudRepository<Customer29, Long> {

	List<Customer29> findByLastName(String lastName);
}
