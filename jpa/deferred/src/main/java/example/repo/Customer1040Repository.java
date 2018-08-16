package example.repo;

import example.model.Customer1040;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1040Repository extends CrudRepository<Customer1040, Long> {

	List<Customer1040> findByLastName(String lastName);
}
