package example.repo;

import example.model.Customer1660;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1660Repository extends CrudRepository<Customer1660, Long> {

	List<Customer1660> findByLastName(String lastName);
}
