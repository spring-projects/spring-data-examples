package example.repo;

import example.model.Customer1015;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1015Repository extends CrudRepository<Customer1015, Long> {

	List<Customer1015> findByLastName(String lastName);
}
