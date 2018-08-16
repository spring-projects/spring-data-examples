package example.repo;

import example.model.Customer1394;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1394Repository extends CrudRepository<Customer1394, Long> {

	List<Customer1394> findByLastName(String lastName);
}
