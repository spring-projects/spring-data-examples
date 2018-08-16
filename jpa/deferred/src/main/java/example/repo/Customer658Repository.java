package example.repo;

import example.model.Customer658;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer658Repository extends CrudRepository<Customer658, Long> {

	List<Customer658> findByLastName(String lastName);
}
