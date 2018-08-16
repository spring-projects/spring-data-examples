package example.repo;

import example.model.Customer872;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer872Repository extends CrudRepository<Customer872, Long> {

	List<Customer872> findByLastName(String lastName);
}
