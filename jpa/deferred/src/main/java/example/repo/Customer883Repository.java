package example.repo;

import example.model.Customer883;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer883Repository extends CrudRepository<Customer883, Long> {

	List<Customer883> findByLastName(String lastName);
}
