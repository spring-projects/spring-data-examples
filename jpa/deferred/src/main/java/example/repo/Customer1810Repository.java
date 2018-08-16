package example.repo;

import example.model.Customer1810;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1810Repository extends CrudRepository<Customer1810, Long> {

	List<Customer1810> findByLastName(String lastName);
}
