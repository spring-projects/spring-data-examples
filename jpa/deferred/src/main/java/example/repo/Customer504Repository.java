package example.repo;

import example.model.Customer504;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer504Repository extends CrudRepository<Customer504, Long> {

	List<Customer504> findByLastName(String lastName);
}
