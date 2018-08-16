package example.repo;

import example.model.Customer1104;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1104Repository extends CrudRepository<Customer1104, Long> {

	List<Customer1104> findByLastName(String lastName);
}
