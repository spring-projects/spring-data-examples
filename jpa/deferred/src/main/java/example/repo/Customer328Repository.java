package example.repo;

import example.model.Customer328;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer328Repository extends CrudRepository<Customer328, Long> {

	List<Customer328> findByLastName(String lastName);
}
