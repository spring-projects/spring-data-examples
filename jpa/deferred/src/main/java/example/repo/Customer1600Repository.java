package example.repo;

import example.model.Customer1600;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1600Repository extends CrudRepository<Customer1600, Long> {

	List<Customer1600> findByLastName(String lastName);
}
