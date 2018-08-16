package example.repo;

import example.model.Customer1861;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1861Repository extends CrudRepository<Customer1861, Long> {

	List<Customer1861> findByLastName(String lastName);
}
