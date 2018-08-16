package example.repo;

import example.model.Customer1636;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1636Repository extends CrudRepository<Customer1636, Long> {

	List<Customer1636> findByLastName(String lastName);
}
