package example.repo;

import example.model.Customer1875;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1875Repository extends CrudRepository<Customer1875, Long> {

	List<Customer1875> findByLastName(String lastName);
}
