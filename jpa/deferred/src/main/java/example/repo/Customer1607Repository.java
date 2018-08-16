package example.repo;

import example.model.Customer1607;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1607Repository extends CrudRepository<Customer1607, Long> {

	List<Customer1607> findByLastName(String lastName);
}
