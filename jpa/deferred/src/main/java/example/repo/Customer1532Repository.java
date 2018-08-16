package example.repo;

import example.model.Customer1532;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1532Repository extends CrudRepository<Customer1532, Long> {

	List<Customer1532> findByLastName(String lastName);
}
