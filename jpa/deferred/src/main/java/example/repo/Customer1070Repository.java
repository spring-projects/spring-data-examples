package example.repo;

import example.model.Customer1070;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1070Repository extends CrudRepository<Customer1070, Long> {

	List<Customer1070> findByLastName(String lastName);
}
