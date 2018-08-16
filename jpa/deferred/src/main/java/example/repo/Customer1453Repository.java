package example.repo;

import example.model.Customer1453;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1453Repository extends CrudRepository<Customer1453, Long> {

	List<Customer1453> findByLastName(String lastName);
}
