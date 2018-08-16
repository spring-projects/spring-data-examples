package example.repo;

import example.model.Customer1103;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1103Repository extends CrudRepository<Customer1103, Long> {

	List<Customer1103> findByLastName(String lastName);
}
