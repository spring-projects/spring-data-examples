package example.repo;

import example.model.Customer101;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer101Repository extends CrudRepository<Customer101, Long> {

	List<Customer101> findByLastName(String lastName);
}
