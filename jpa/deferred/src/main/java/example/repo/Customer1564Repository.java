package example.repo;

import example.model.Customer1564;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1564Repository extends CrudRepository<Customer1564, Long> {

	List<Customer1564> findByLastName(String lastName);
}
