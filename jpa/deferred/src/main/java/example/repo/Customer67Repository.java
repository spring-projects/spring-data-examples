package example.repo;

import example.model.Customer67;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer67Repository extends CrudRepository<Customer67, Long> {

	List<Customer67> findByLastName(String lastName);
}
