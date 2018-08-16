package example.repo;

import example.model.Customer850;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer850Repository extends CrudRepository<Customer850, Long> {

	List<Customer850> findByLastName(String lastName);
}
