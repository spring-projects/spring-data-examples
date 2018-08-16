package example.repo;

import example.model.Customer1159;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1159Repository extends CrudRepository<Customer1159, Long> {

	List<Customer1159> findByLastName(String lastName);
}
