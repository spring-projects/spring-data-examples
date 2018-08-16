package example.repo;

import example.model.Customer189;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer189Repository extends CrudRepository<Customer189, Long> {

	List<Customer189> findByLastName(String lastName);
}
