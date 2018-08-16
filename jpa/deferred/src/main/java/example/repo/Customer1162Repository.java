package example.repo;

import example.model.Customer1162;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1162Repository extends CrudRepository<Customer1162, Long> {

	List<Customer1162> findByLastName(String lastName);
}
