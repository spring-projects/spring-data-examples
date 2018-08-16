package example.repo;

import example.model.Customer1689;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1689Repository extends CrudRepository<Customer1689, Long> {

	List<Customer1689> findByLastName(String lastName);
}
