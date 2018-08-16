package example.repo;

import example.model.Customer1836;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1836Repository extends CrudRepository<Customer1836, Long> {

	List<Customer1836> findByLastName(String lastName);
}
