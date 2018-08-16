package example.repo;

import example.model.Customer582;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer582Repository extends CrudRepository<Customer582, Long> {

	List<Customer582> findByLastName(String lastName);
}
