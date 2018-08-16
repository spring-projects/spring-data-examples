package example.repo;

import example.model.Customer842;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer842Repository extends CrudRepository<Customer842, Long> {

	List<Customer842> findByLastName(String lastName);
}
