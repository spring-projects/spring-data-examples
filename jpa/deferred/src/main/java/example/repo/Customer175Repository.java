package example.repo;

import example.model.Customer175;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer175Repository extends CrudRepository<Customer175, Long> {

	List<Customer175> findByLastName(String lastName);
}
