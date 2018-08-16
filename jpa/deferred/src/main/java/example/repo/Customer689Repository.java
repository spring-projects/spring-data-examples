package example.repo;

import example.model.Customer689;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer689Repository extends CrudRepository<Customer689, Long> {

	List<Customer689> findByLastName(String lastName);
}
