package example.repo;

import example.model.Customer474;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer474Repository extends CrudRepository<Customer474, Long> {

	List<Customer474> findByLastName(String lastName);
}
