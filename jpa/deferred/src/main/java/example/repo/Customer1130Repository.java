package example.repo;

import example.model.Customer1130;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1130Repository extends CrudRepository<Customer1130, Long> {

	List<Customer1130> findByLastName(String lastName);
}
