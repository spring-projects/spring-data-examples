package example.repo;

import example.model.Customer1516;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1516Repository extends CrudRepository<Customer1516, Long> {

	List<Customer1516> findByLastName(String lastName);
}
