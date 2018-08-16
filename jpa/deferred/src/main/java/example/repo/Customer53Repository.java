package example.repo;

import example.model.Customer53;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer53Repository extends CrudRepository<Customer53, Long> {

	List<Customer53> findByLastName(String lastName);
}
