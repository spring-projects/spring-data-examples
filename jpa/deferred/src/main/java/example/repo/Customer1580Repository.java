package example.repo;

import example.model.Customer1580;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1580Repository extends CrudRepository<Customer1580, Long> {

	List<Customer1580> findByLastName(String lastName);
}
