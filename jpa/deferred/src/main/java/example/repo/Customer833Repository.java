package example.repo;

import example.model.Customer833;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer833Repository extends CrudRepository<Customer833, Long> {

	List<Customer833> findByLastName(String lastName);
}
