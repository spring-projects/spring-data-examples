package example.repo;

import example.model.Customer895;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer895Repository extends CrudRepository<Customer895, Long> {

	List<Customer895> findByLastName(String lastName);
}
