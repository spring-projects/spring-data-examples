package example.repo;

import example.model.Customer589;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer589Repository extends CrudRepository<Customer589, Long> {

	List<Customer589> findByLastName(String lastName);
}
