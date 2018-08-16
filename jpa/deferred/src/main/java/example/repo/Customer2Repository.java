package example.repo;

import example.model.Customer2;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer2Repository extends CrudRepository<Customer2, Long> {

	List<Customer2> findByLastName(String lastName);
}
