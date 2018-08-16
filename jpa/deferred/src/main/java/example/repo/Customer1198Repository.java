package example.repo;

import example.model.Customer1198;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1198Repository extends CrudRepository<Customer1198, Long> {

	List<Customer1198> findByLastName(String lastName);
}
