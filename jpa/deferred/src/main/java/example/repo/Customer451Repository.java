package example.repo;

import example.model.Customer451;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer451Repository extends CrudRepository<Customer451, Long> {

	List<Customer451> findByLastName(String lastName);
}
