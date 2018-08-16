package example.repo;

import example.model.Customer338;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer338Repository extends CrudRepository<Customer338, Long> {

	List<Customer338> findByLastName(String lastName);
}
