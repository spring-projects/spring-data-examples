package example.repo;

import example.model.Customer105;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer105Repository extends CrudRepository<Customer105, Long> {

	List<Customer105> findByLastName(String lastName);
}
