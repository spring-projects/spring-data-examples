package example.repo;

import example.model.Customer1774;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1774Repository extends CrudRepository<Customer1774, Long> {

	List<Customer1774> findByLastName(String lastName);
}
