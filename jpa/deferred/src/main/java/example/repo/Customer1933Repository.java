package example.repo;

import example.model.Customer1933;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1933Repository extends CrudRepository<Customer1933, Long> {

	List<Customer1933> findByLastName(String lastName);
}
