package example.repo;

import example.model.Customer1115;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1115Repository extends CrudRepository<Customer1115, Long> {

	List<Customer1115> findByLastName(String lastName);
}
