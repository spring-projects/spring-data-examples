package example.repo;

import example.model.Customer1790;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1790Repository extends CrudRepository<Customer1790, Long> {

	List<Customer1790> findByLastName(String lastName);
}
