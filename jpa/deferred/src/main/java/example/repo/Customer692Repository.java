package example.repo;

import example.model.Customer692;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer692Repository extends CrudRepository<Customer692, Long> {

	List<Customer692> findByLastName(String lastName);
}
