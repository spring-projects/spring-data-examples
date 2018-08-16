package example.repo;

import example.model.Customer1844;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1844Repository extends CrudRepository<Customer1844, Long> {

	List<Customer1844> findByLastName(String lastName);
}
