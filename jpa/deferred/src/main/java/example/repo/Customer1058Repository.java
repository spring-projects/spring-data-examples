package example.repo;

import example.model.Customer1058;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1058Repository extends CrudRepository<Customer1058, Long> {

	List<Customer1058> findByLastName(String lastName);
}
