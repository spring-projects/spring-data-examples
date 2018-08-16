package example.repo;

import example.model.Customer987;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer987Repository extends CrudRepository<Customer987, Long> {

	List<Customer987> findByLastName(String lastName);
}
