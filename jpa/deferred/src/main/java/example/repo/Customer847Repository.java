package example.repo;

import example.model.Customer847;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer847Repository extends CrudRepository<Customer847, Long> {

	List<Customer847> findByLastName(String lastName);
}
