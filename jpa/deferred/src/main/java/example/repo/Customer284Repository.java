package example.repo;

import example.model.Customer284;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer284Repository extends CrudRepository<Customer284, Long> {

	List<Customer284> findByLastName(String lastName);
}
