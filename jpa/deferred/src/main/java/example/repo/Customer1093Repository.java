package example.repo;

import example.model.Customer1093;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1093Repository extends CrudRepository<Customer1093, Long> {

	List<Customer1093> findByLastName(String lastName);
}
