package example.repo;

import example.model.Customer875;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer875Repository extends CrudRepository<Customer875, Long> {

	List<Customer875> findByLastName(String lastName);
}
