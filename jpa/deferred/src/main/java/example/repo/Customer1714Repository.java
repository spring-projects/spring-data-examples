package example.repo;

import example.model.Customer1714;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1714Repository extends CrudRepository<Customer1714, Long> {

	List<Customer1714> findByLastName(String lastName);
}
