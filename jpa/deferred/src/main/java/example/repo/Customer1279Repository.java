package example.repo;

import example.model.Customer1279;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1279Repository extends CrudRepository<Customer1279, Long> {

	List<Customer1279> findByLastName(String lastName);
}
