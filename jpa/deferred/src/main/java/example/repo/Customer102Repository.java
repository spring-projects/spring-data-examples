package example.repo;

import example.model.Customer102;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer102Repository extends CrudRepository<Customer102, Long> {

	List<Customer102> findByLastName(String lastName);
}
