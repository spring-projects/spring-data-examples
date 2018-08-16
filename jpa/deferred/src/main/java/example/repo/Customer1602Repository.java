package example.repo;

import example.model.Customer1602;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1602Repository extends CrudRepository<Customer1602, Long> {

	List<Customer1602> findByLastName(String lastName);
}
