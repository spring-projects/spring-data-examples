package example.repo;

import example.model.Customer1312;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1312Repository extends CrudRepository<Customer1312, Long> {

	List<Customer1312> findByLastName(String lastName);
}
